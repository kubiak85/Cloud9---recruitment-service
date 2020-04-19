package com.serverless.handlers.form;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.db.FormDBTable;
import com.serverless.db.FormSolutionsDBTable;
import com.serverless.db.model.Form;
import com.serverless.db.model.FormSolutions;
import com.serverless.db.model.User;
import com.serverless.handlers.pojo.ApiRespnsesHandlerPojo;
import com.serverless.handlers.pojo.ResponseErrorMessagePojo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindUserFormHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final Logger LOG = LogManager.getLogger(FindUserFormHandler.class);
    private List<Form> getAllFormsForUser(String userId) throws IOException {
        return new FormDBTable()
                .listForms()
                .stream()
                .filter(u -> u.getUsers() != null)
                .filter(u -> u.getUsers().indexOf(userId) >= 0)
                .collect(Collectors.toList());
    }

    private List<String> getFormSolutionWithAnswer(String userId) throws IOException {
        List<String> results = new ArrayList<>();

        List<FormSolutions> formSolutions = new FormSolutionsDBTable()
                .listFormSolutions()
                .stream()
                .filter(f -> f.getUserId().compareTo(userId) == 0)
                .filter(f -> f.getAnswers().size() > 0)
                .collect(Collectors.toList());

        formSolutions.forEach(v -> results.add(v.getFormId()));
        return results;
    }

    private List<Form> getFilteredForm(String userId) throws IOException {
        List<Form> results = new ArrayList<>();
        List<String> formsString = this.getFormSolutionWithAnswer(userId);
        List<Form> forms = this.getAllFormsForUser(userId);
        for(Form form: forms)
            if(!formsString.contains(form.getId()))
                results.add(form);

        return results;
    }
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try{
            Map<String,String> pathParameters =  (Map<String,String>)input.get("pathParameters");
            String userId = pathParameters.get("id");

            if(userId == null){
                return ApiRespnsesHandlerPojo.sendResponse("You must add userId to path.", 200);
            }
            return ApiRespnsesHandlerPojo.sendResponse(getFilteredForm(userId), 200);
        }
        catch (Exception e){
            LOG.info("Call FindUserFormHandler::handleRequest(" + input + ", " + context + ")");
            return ApiRespnsesHandlerPojo.sendResponse(new ResponseErrorMessagePojo(e.toString()), 200);
        }

    }
}