package com.lodoviko.convencao.api.exceptionhandler;

import com.lodoviko.convencao.domain.exception.EntidadeNaoEncontradaException;
import com.lodoviko.convencao.domain.exception.RecursoJaCadastradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";
    public static final String MSG_ERRO_ENTIDADE_EM_USO = "O registro que você está tentando manipular está em uso.";

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
       return handlervalidationInternal(ex, ex.getBindingResult(), headers, (HttpStatus) status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if(body == null) {
            body = Problem.builder()
                    .dsTitulo(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                    .cdStatus(statusCode.value())
                    .dsMensUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .dtData(OffsetDateTime.now())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .dsTitulo((String) body)
                    .cdStatus(statusCode.value())
                    .dsMensUsuario(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .dtData(OffsetDateTime.now())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail, detail, null).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    // Resolve erros quando for informado campos com tipo diferente do esperado
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.PARAMETRO_INVALIDO;
        var detail = String.format("O parâmetro da URL '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        var problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.ERRO_DE_SISTEMA;
        var detail = String.format("NullPointerException capturado:  %s", ex.getMessage());
        var problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();

        log.info(String.format("ERRO: %s", ex));
        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.ERRO_DE_SISTEMA;
        var detail = String.format("Erro de integridade no banco de dados:  %s", ex.getMessage());
        var problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_GENERICA_USUARIO_FINAL, null).build();

        log.info(String.format("ERRO: %s", ex));
        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(RecursoJaCadastradoException.class)
    public ResponseEntity<?> handleRecursoJaCadastradoException(RecursoJaCadastradoException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.RECURSO_JA_CADASTRADO;
        var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail, detail, null).build();

        log.info(String.format("ERRO: %s", ex));
        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problemType = ProblemType.ENTIDADE_EM_USO;
        var detail = ex.getMessage();
        var problem = createProblemBuilder(status, problemType, detail, MSG_ERRO_ENTIDADE_EM_USO, null).build();

        log.info(String.format("ERRO: %s", ex));
        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlervalidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problem.Objeto> problemObjeto = bindingResult.getAllErrors().stream()
                .map(objectError ->  {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();

                    if(objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Objeto.builder()
                            .dsNome(name)
                            .dsMensUsuario(message)
                            .build();
                })
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detail, detail, problemObjeto).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail, String userMessage, List<Problem.Objeto> objectFields) {
        return  Problem.builder()
                .cdStatus(status.value())
                .dtData(OffsetDateTime.now())
                .dsTipo(problemType.getUri())
                .dsTitulo(problemType.getTitle())
                .dsDetalhe(detail)
                .dsMensUsuario(userMessage)
                .lsObjetos(objectFields);
    }

    // Exemplo para capturar os erros
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(DataIntegrityViolationException e) {
//
//        System.out.println(e.getMessage());
//        System.out.println(e.getCause());
//        System.out.println(e.getRootCause());
//        System.out.println(e.fillInStackTrace());
//        System.out.println(e.getLocalizedMessage());
//
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERRO Capturado");
//    }
}
