import spark.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.staticFileLocation;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        ProcessBuilder process = new ProcessBuilder();
        int port;

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);


        get("/", (req, res)-> new ModelAndView(new HashMap<>(), "index.hbs"), new HandlebarsTemplateEngine());
        get("/animals/endangered", (req, res)-> new ModelAndView(new HashMap<>(), "endangered_species.hbs"), new HandlebarsTemplateEngine());
        get("/animals/thriving", (req, res)-> new ModelAndView(new HashMap<>(), "other-species.hbs"), new HandlebarsTemplateEngine());
        get("/add/new", (req, res)-> new ModelAndView(new HashMap<>(), "add-animal.hbs"), new HandlebarsTemplateEngine());

        get("/add/endangered", (req, res)->{
            Map<String, Object> model = new HashMap<>();
            List<EndangeredAnimal> endangeredAnimals = EndangeredAnimal.getEndangeredAnimals();
            String[] ageTypes = EndangeredAnimal.ageTypes;
            String[] healthStatuses = EndangeredAnimal.healthStatuses;
            String type = EndangeredAnimal.getDatabaseType();
            model.put("ageTypes", ageTypes);
            model.put("healthStatuses", healthStatuses);
            model.put("type", type);
            model.put("endangeredAnimals", endangeredAnimals);
            return  new ModelAndView(model, "add-endangered.hbs");
            }, new HandlebarsTemplateEngine());

        get("/add/thriving", (req, res)-> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = Animal.getAnimals();
            String[] ageTypes = Animal.ageTypes;
            String[] healthStatuses = Animal.healthStatuses;
            String type = Animal.getDatabaseType();
            model.put("ageTypes",ageTypes);
            model.put("healthTypes", healthStatuses);
            model.put("type", type);
            model.put("thrivingAnimals", animals                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        );
            return new ModelAndView(model, "add-thriving.hbs");
            }, new HandlebarsTemplateEngine());


        get("/add/sight", (req, res)-> {
            Map<String, Object> model = new HashMap<>();
            List<Sighting> sightings = Sighting.getSightings();
            List<Animal> animals = Sighting.getAll();
            model.put("animals", animals);
            model.put("sightings", sightings);
            return new ModelAndView(model, "submit_sighting.hbs");
        },new HandlebarsTemplateEngine());

        post("/new/endangered", (req, res)-> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String age = req.queryParams("age");
            String health = req.queryParams("health");
            EndangeredAnimal animal = new EndangeredAnimal(name, age, health);
            animal.saveEndangered();
            model.put("endangeredAnimal", animal);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        post("/new/thriving", (req, res)-> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String age = req.queryParams("age");
            String health = req.queryParams("health");
            Animal animal = new Animal(name, health, age);
            animal.saveAnimal();
            model.put("thrivingAnimal", animal);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        post("/new/sight", (req, res)-> {
            Map<String, Object> model = new HashMap<>();
            int animalname = Integer.parseInt(req.queryParams("name"));
            String rangername = req.queryParams("ranger");
            String location = req.queryParams("location");
            Sighting sighting = new Sighting(animalname, location, rangername );
            sighting.saveSighting();
            model.put("sight", sighting);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
