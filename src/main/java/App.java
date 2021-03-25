import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.staticFileLocation;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);


        //show home page
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<EndangeredAnimal> animals = EndangeredAnimal.all();
            model.put("animals",animals);
            model.put("sightings",Sighting.all());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get all animals
        get("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<EndangeredAnimal> animals = EndangeredAnimal.all();
            model.put("animals",animals);
            model.put("sightings",Sighting.all());
            return new ModelAndView(model, "animals.hbs");
        }, new HandlebarsTemplateEngine());

        //get all sightings
        get("/sightings", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Sighting> sightings = Sighting.all();
            model.put("animals", EndangeredAnimal.all());
            model.put("sightings", sightings);
            return new ModelAndView(model, "sightings.hbs");
        }, new HandlebarsTemplateEngine());

        //get animal by id
        get("/animal-details/:id",(req, res) ->{
            Map<String, Object> model = new HashMap<>();
            int idOfEndangeredAnimal=Integer.parseInt(req.params("id"));
            EndangeredAnimal foundAnimal=EndangeredAnimal.find(idOfEndangeredAnimal);
            model.put("animals",foundAnimal);
            model.put("sightings",Sighting.all());
            return new ModelAndView(model, "animal-details.hbs");
        }, new HandlebarsTemplateEngine());

        //get sighting by id
        get("/sighting-details/:id",(req, res) ->{
            Map<String, Object> model = new HashMap<>();
            int idOfSighting=Integer.parseInt(req.params("id"));
            Sighting foundSighting=Sighting.find(idOfSighting);
            EndangeredAnimal enim = EndangeredAnimal.find(foundSighting.getAnimalid());
            System.out.println(foundSighting.getRangername());
            model.put("sightings",foundSighting);
            model.put("animal",enim);
            model.put("animals",EndangeredAnimal.all());
            return new ModelAndView(model, "sighting-details.hbs");
        }, new HandlebarsTemplateEngine());

        //show new animal form
        get("/posts/animals/new",(req, res) ->{
            Map<String, Object> model = new HashMap<>();
            model.put("animals",EndangeredAnimal.all());
            model.put("sightings",Sighting.all());
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //show new endangered animal form
        get("/posts/endangered-animals/new",(req, res) ->{
            Map<String, Object> model = new HashMap<>();
            model.put("animals",EndangeredAnimal.all());
            model.put("sightings",Sighting.all());
            return new ModelAndView(model, "endangered-animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //show new sighting form
        get("/posts/sightings/new",(req, res) ->{
            Map<String, Object> model = new HashMap<>();
            model.put("sightings",Sighting.all());
            model.put("animals",EndangeredAnimal.all());
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual animal
        get("/animals/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAnimalToDelete = Integer.parseInt(req.params("id"));
            EndangeredAnimal deleteAnimal = EndangeredAnimal.find(idOfAnimalToDelete);
            deleteAnimal.delete();
            return new ModelAndView(model, "animals.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual sighting
        get("/sightings/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSightingToDelete = Integer.parseInt(req.params("id"));
            Sighting deleteSighting = Sighting.find(idOfSightingToDelete);
            deleteSighting.delete();
            return new ModelAndView(model, "sightings.hbs");
        }, new HandlebarsTemplateEngine());

        //process new animals form
        post("/posts/animals/new",(req, res) ->{
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String health = req.queryParams("health");
            String age = req.queryParams("age");
            EndangeredAnimal animal = new EndangeredAnimal(name,health,age);
            animal.save();
            model.put("sightings",Sighting.all());
            model.put("animals",EndangeredAnimal.all());
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process new sightings form
        post("/posts/sightings/new",(req, res) ->{
            Map<String, Object> model = new HashMap<>();
            String location = req.queryParams("location");
            String rangername = req.queryParams("rangername");
            int animalid = Integer.parseInt(req.queryParams("animalid"));
            Sighting sighting = new Sighting(location,rangername,animalid);
            sighting.save();
            model.put("sightings",Sighting.all());
            model.put("animals",EndangeredAnimal.all());
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
