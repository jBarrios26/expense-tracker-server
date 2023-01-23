package com.jorgebarrios.expensetracker.user;

import com.jorgebarrios.expensetracker.auth.exception.TokenRefreshException;
import com.jorgebarrios.expensetracker.auth.model.RefreshToken;
import com.jorgebarrios.expensetracker.auth.service.AuthService;
import com.jorgebarrios.expensetracker.common.exception.BudgetUserNotFoundException;
import com.jorgebarrios.expensetracker.security.JwtUtils;
import com.jorgebarrios.expensetracker.user.input.LoginInput;
import com.jorgebarrios.expensetracker.user.input.SignUpInput;
import com.jorgebarrios.expensetracker.user.response.BudgetUserInfoResponse;
import com.jorgebarrios.expensetracker.user.response.LoginResponse;
import com.jorgebarrios.expensetracker.user.response.RefreshTokenDTO;
import com.jorgebarrios.expensetracker.user.response.SignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/user")
@RequiredArgsConstructor
public class BudgetUserController {

    private final BudgetUserService budgetUserService;
    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final BudgetUserRepository budgetUserRepository;

    @GetMapping(name = "helloWorld", path = "/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping(path = "/sign-up")
    public SignUpResponse signUp(@RequestBody SignUpInput signUpInput) {
        return new SignUpResponse(budgetUserService.saveBudgetUser(
                signUpInput.getName(),
                signUpInput.getLastName(),
                signUpInput.getEmail(),
                encoder.encode(signUpInput.getPassword()),
                signUpInput.getGender()
                                                                  ));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginInput loginInput) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginInput.getEmail(),
                loginInput.getPassword()
                          .trim()
        ));

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(loginInput.getEmail());
        Optional<BudgetUser> user =
                budgetUserRepository.findBudgetUserByEmail(loginInput.getEmail());
        if (user.isEmpty()) {
            throw new BudgetUserNotFoundException(loginInput.getEmail());
        }
        if (userDetails != null) {
            final String token = jwtUtils.generateToken(userDetails);
            RefreshToken refreshToken =
                    authService.createRefreshToken(user.get()
                                                       .getId()
                                                       .toString());
            return ResponseEntity.ok(new LoginResponse(
                                             true,
                                             token,
                                             refreshToken.getToken(),
                                             user.get()
                                                 .getId()
                                                 .toString()
                                     )
                                    );
        }
        return ResponseEntity.ok()
                             .body(new LoginResponse(
                                           false,
                                           "",
                                           "",
                                           ""
                                   )
                                  );
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenDTO request) {
        String requestRefreshToken = request.getRefreshToken();

        return authService.findByToken(requestRefreshToken)
                          .map(authService::verifyExpiration)
                          .map(RefreshToken::getUser)
                          .map(user -> {
                              final UserDetails userDetails =
                                      userDetailsService.loadUserByUsername(user.getEmail());
                              String token =
                                      jwtUtils.generateToken(userDetails);
                              RefreshToken refreshToken =
                                      authService.createRefreshToken(user.getId()
                                                                         .toString());

                              return ResponseEntity.ok(new LoginResponse(
                                      true,
                                      token,
                                      refreshToken.getToken(),
                                      user.getId()
                                          .toString()
                              ));
                          })
                          .orElseThrow(() -> new TokenRefreshException(
                                  requestRefreshToken,
                                  "Refresh token is not in database!"
                          ));
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<BudgetUserInfoResponse> getUserInfo(@Valid @PathVariable(required = true, name = "userId") String userId) {
        BudgetUser user = budgetUserService.getUser(userId);
        return ResponseEntity.ok(new BudgetUserInfoResponse(
                user.getId()
                    .toString(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail()
        ));
    }
}
