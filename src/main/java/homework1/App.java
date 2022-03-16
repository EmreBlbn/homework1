/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package homework1;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;


public class App {
    public static boolean search(ArrayList<Integer> array, int e) {
        System.out.println("inside search");
        if (array == null) return false;
  
        for (int elt : array) {
          if (elt == e) return true;
        }
        return false;
    }

    public static <T extends Number,R extends Number,S extends Number> boolean searchForTwo(ArrayList<T> array,R element1,S element2){
        if (array == null) return false;

        boolean found1=false,found2=false;

        for (T elt: array){
          if (elt.doubleValue()==element1.doubleValue()) found1=true;
          if (elt.doubleValue()==element2.doubleValue()) found2=true;
        }

        return found1 || found2;
    }

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
      Logger logger =Logger.getLogger(App.class.getName());
      

      int port = Integer.parseInt(System.getenv("PORT"));
      port(port);

      port(getHerokuAssignedPort());

      get("/", (req, res) -> "Hello, World");

      post("/compute", (req, res) -> {
        //System.out.println(req.queryParams("input1"));
        //System.out.println(req.queryParams("input2"));

        /*
        String input1 = req.queryParams("input1");
        java.util.Scanner sc1 = new java.util.Scanner(input1);
        sc1.useDelimiter("[;\r\n]+");
        java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
        while (sc1.hasNext())
        {
          int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
          inputList.add(value);
        }
        sc1.close();
        System.out.println(inputList);


        String input2 = req.queryParams("input2").replaceAll("\\s","");
        int input2AsInt = Integer.parseInt(input2);

        boolean result = App.search(inputList, input2AsInt);
*/
        String input1 = req.queryParams("input1");
        java.util.Scanner sc1 = new java.util.Scanner(input1);
        sc1.useDelimiter("[;\r\n]+");
        java.util.ArrayList<Number> inputList = new java.util.ArrayList<>();
        while (sc1.hasNext())
        {
          Number value = Double.parseDouble(sc1.next().replaceAll("\\s",""));
          inputList.add(value);
        }
        sc1.close();
        System.out.println(inputList);


        String input2 = req.queryParams("input2").replaceAll("\\s","");
        Number input2AsInt = Double.parseDouble(input2);

        boolean result = App.searchForTwo(inputList, input2AsInt,2);

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("result", result);
        return new ModelAndView(map, "compute.mustache");
      }, new MustacheTemplateEngine());


      get("/compute",
          (rq, rs) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
          },
          new MustacheTemplateEngine());
  }

  static int getHerokuAssignedPort() {
      ProcessBuilder processBuilder = new ProcessBuilder();
      if (processBuilder.environment().get("PORT") != null) {
          return Integer.parseInt(processBuilder.environment().get("PORT"));
      }
      return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }
}
