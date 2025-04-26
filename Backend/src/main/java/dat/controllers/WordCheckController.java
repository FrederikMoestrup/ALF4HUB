package dat.controllers;

import dat.services.OffensiveWordsService;
import io.javalin.http.Context;

public class WordCheckController {

     private final OffensiveWordsService offensiveWordsService = new OffensiveWordsService();

     public void textChecking(Context ctx) {

          String txtInput = ctx.body();
          boolean isOffensive = offensiveWordsService.offensiveWords(txtInput);

          if (isOffensive) {
               ctx.status(400).result("Contains offensive word!");
          } else {
               ctx.status(200).result("Word accepted");
          }
     }

}
