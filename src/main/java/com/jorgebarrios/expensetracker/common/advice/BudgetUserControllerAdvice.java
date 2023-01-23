package com.jorgebarrios.expensetracker.common.advice;

import com.jorgebarrios.expensetracker.auth.exception.TokenRefreshException;
import com.jorgebarrios.expensetracker.budget.exception.BudgetCreateException;
import com.jorgebarrios.expensetracker.budget.exception.UnknownBudgetCategoryException;
import com.jorgebarrios.expensetracker.common.BasicErrorResponse;
import com.jorgebarrios.expensetracker.common.exception.BudgetUserNotFoundException;
import com.jorgebarrios.expensetracker.user.exceptions.UserAlreadyRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(
        basePackages = {"com.jorgebarrios.expensetracker.user", "com" +
                                                                ".jorgebarrios.expensetracker.budget"
        }
)
public class BudgetUserControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisterException.class)
    ResponseEntity<BasicErrorResponse> userAlreadyRegisterHandler(UserAlreadyRegisterException userAlreadyRegisterException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(
                                     new BasicErrorResponse(
                                             1,
                                             userAlreadyRegisterException.getMessage()
                                     )
                                  );
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<BasicErrorResponse> authenticationFail(AuthenticationException userAlreadyRegisterException) {
        userAlreadyRegisterException.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(
                                     new BasicErrorResponse(
                                             2,
                                             userAlreadyRegisterException.getMessage()
                                     )
                                  );
    }

    @ExceptionHandler(BudgetUserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<BasicErrorResponse> budgetUserNotFoundHandler(BudgetUserNotFoundException budgetUserNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(
                                     new BasicErrorResponse(
                                             3,
                                             budgetUserNotFoundException.getMessage()
                                     )
                                  );
    }

    @ExceptionHandler(UnknownBudgetCategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<BasicErrorResponse> unknownBudgetCategoryException(UnknownBudgetCategoryException unknownBudgetCategoryException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(
                                     new BasicErrorResponse(
                                             4,
                                             unknownBudgetCategoryException.getMessage()
                                     )
                                  );
    }

    @ExceptionHandler(BudgetCreateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<BasicErrorResponse> budgetCreateExceptionHandler(BudgetCreateException budgetCreateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(
                                     new BasicErrorResponse(
                                             5,
                                             budgetCreateException.getMessage()
                                     )
                                  );
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<BasicErrorResponse> handleTokenRefreshException(TokenRefreshException tokenRefreshException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(
                                     new BasicErrorResponse(
                                             6,
                                             tokenRefreshException.getMessage()
                                     )
                                  );

    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<BasicErrorResponse> runtimeExceptionAdvice(RuntimeException runtimeException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(
                                     new BasicErrorResponse(
                                             7,
                                             runtimeException.getMessage()
                                     )
                             );
    }

}
