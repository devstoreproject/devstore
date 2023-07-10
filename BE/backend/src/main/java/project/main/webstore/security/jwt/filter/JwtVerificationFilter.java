package project.main.webstore.security.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer tokenizer;

    private void setAuthenticationToContext(Map<String, Object> claims) {
        LinkedHashMap userInfoMap = (LinkedHashMap) claims.get("userInfo");

        //리펙토링 대상
        Long id =  Long.valueOf((String)userInfoMap.get("userId"));
        String email = (String) userInfoMap.get("email");
        String nickName = (String) userInfoMap.get("nickName");
        UserRole userRole = UserRole.transEnum((String) userInfoMap.get("userRole"));
        UserInfoDto userInfo = new UserInfoDto(id, email, nickName, userRole);


        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(userInfo.getUserRole().getRole());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        //예외 발생 사유를 하나하나 체크
        } catch (SignatureException se) {
            request.setAttribute("exception",se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception",ee);
        } catch (Exception e) {
            request.setAttribute("exception",e);
        }
        response.getHeaderNames().iterator().forEachRemaining(s-> log.info("#####" + response.getHeaders(s)));

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer", "");
        String base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(tokenizer.getSecretKey());
        Map<String,Object> claims = tokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
        return claims;
    }
}
