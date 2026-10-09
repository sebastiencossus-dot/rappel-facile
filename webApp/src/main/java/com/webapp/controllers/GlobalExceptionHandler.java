package com.webapp.controllers;

import feign.FeignException;
import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ModelAndView errorPage(
            HttpStatus status,
            String title,
            String message,
            String icon) {

        ModelAndView mav = new ModelAndView("error/error-page");

        mav.setStatus(status);
        mav.addObject("code", status.value());
        mav.addObject("title", title);
        mav.addObject("message", message);
        mav.addObject("icon", icon);

        return mav;
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ModelAndView handle400(FeignException.BadRequest ex) {
        return errorPage(
                HttpStatus.BAD_REQUEST,
                "Requête incorrecte",
                "Certaines informations transmises sont incorrectes.",
                "bi bi-exclamation-triangle"
        );
    }

    @ExceptionHandler(FeignException.Forbidden.class)
    public ModelAndView handle403(FeignException.Forbidden ex) {
        return errorPage(
                HttpStatus.FORBIDDEN,
                "Accès refusé",
                "Vous n'êtes pas autorisé à accéder à cette ressource.",
                "bi bi-shield-lock"
        );
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ModelAndView handle404(FeignException.NotFound ex) {
        return errorPage(
                HttpStatus.NOT_FOUND,
                "Ressource introuvable",
                "Le rendez-vous ou la page demandée est introuvable.",
                "bi bi-calendar-x"
        );
    }

    @ExceptionHandler(FeignException.InternalServerError.class)
    public ModelAndView handle500(FeignException.InternalServerError ex) {
        return errorPage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erreur technique",
                "Une erreur technique est survenue. Veuillez réessayer plus tard.",
                "bi bi-gear"
        );
    }

    @ExceptionHandler(RetryableException.class)
    public ModelAndView handle503(RetryableException ex) {
        return errorPage(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Service indisponible",
                "Un service est momentanément indisponible. Veuillez réessayer plus tard.",
                "bi bi-cloud-slash"
        );
    }
}