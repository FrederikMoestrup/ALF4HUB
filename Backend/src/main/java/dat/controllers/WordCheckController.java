package dat.controllers;

import dat.services.OffensiveWordsService;
import io.javalin.http.Context;
import dat.exceptions.ApiException;

/**
 * Controller class that manages the HTTP logic.
 * Handles incoming input and sends a response.
 */


public class WordCheckController {

     private final OffensiveWordsService offensiveWordsService = new OffensiveWordsService();

     public void textChecking(Context ctx) throws ApiException {
          String txtInput = ctx.body(); // I fetch the entire body content from the HTTP request
          boolean isOffensive = offensiveWordsService.offensiveWords(txtInput); // checking offensive words

          if (isOffensive) {
               throw new ApiException(400, "Contains offensive word!");
          } else {
               ctx.status(200).result("Word accepted");
          }
     }
}
