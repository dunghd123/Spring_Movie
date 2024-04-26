package ra.spring_movie.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.spring_movie.model.UserCustomDetail;
import ra.spring_movie.services.impl.UserDetailServiceImpl;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtToken;
    private final UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull  FilterChain filterChain) throws ServletException, IOException {
        final String authentication= request.getHeader("Authorization");
        final String jwt;
        final String username;
        if(authentication==null ||!authentication.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt= authentication.substring(7);
        username= jwtToken.extractUsername(jwt);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserCustomDetail userCustomDetail= (UserCustomDetail) this.userDetailService.loadUserByUsername(username);
            if(jwtToken.isTokenValid(jwt,userCustomDetail)){
                UsernamePasswordAuthenticationToken authen= new UsernamePasswordAuthenticationToken(
                        userCustomDetail,
                        null,
                        userCustomDetail.getAuthorities()
                );
                authen.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authen);
            }
        }
        filterChain.doFilter(request,response);
    }
}
