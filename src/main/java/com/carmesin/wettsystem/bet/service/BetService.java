package com.carmesin.wettsystem.bet.service;

import com.carmesin.wettsystem.bet.model.HorseModel;
import com.carmesin.wettsystem.bet.model.Leagues;
import com.carmesin.wettsystem.bet.repository.HorseRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class BetService {

    @Autowired
    private HorseRepository horseRepository;

    public JsonArray calculateQuotes() {

        List<HorseModel> allHorses = horseRepository.findAll(); // Load all horses from database
        double allTotalGerman = calculateAllTotal(allHorses, Leagues.GERMAN);
        double allTotalRussian = calculateAllTotal(allHorses, Leagues.RUSSIA);
        int allTotalChampionsLeague = calculateAllTotalChampion(allHorses, Leagues.CHAMPIONS_LEAGUE);
        //int allTotalChampionsLeague2 = calculateAllTotalChampion(allHorses, Leagues.CHAMPIONS_LEAGUE);
        //Second Game -> repetitions with the horses

        JsonArray horses = new JsonArray();

        allHorses.forEach(horse -> {

            //every horse get his total value
            int total = calculateTotal(horse);

            //4 Pferde pro Liga in Champions League und daraus 2 Rennen mit jeweils 3 Random pferden aus den 8


            double quote;
            if (horse.getLeague() == Leagues.GERMAN) {
                quote = (allTotalGerman - total) / total;
                //german.put(horse.getName(), quote);
            } else if (horse.getLeague() == Leagues.RUSSIA) {
                quote = (allTotalRussian - total) / total;
                //russia.put(horse.getName(), quote);
            } else {
                quote = (allTotalChampionsLeague - total) / total;
            }

            quote = Math.round(quote * 100.0 )/ 100.0;

            JsonObject horseJson = new JsonObject();
            horseJson.addProperty("name", horse.getName());
            horseJson.addProperty("quote", quote);
            horseJson.addProperty("league", horse.getLeague().toString());

            horses.add(horseJson);
        });

        return horses;

    }





    public JsonArray calculateQuotesGerman() {

        List<HorseModel> allHorses = horseRepository.findAll(); // Load all horses from database
        double allTotalGerman = calculateAllTotal(allHorses, Leagues.GERMAN);
        JsonArray horses = new JsonArray();
        allHorses.forEach(horse -> {

            //every horse get his total value
            int total = calculateTotal(horse);

            //4 Pferde pro Liga in Champions League und daraus 2 Rennen mit jeweils 3 Random pferden aus den 8


            double quote;
            if (horse.getLeague() == Leagues.GERMAN) {
                quote = (allTotalGerman - total) / total;
                quote = Math.round(quote * 10) / 10;

                JsonObject horseJson = new JsonObject();
                horseJson.addProperty("name", horse.getName());
                horseJson.addProperty("quote", quote);

                horses.add(horseJson);
            }

        });

        return horses;

    }





    public HashMap<String, Double> calculateQuotesRussia(Model model) {

        List<HorseModel> allHorses = horseRepository.findAll(); // Load all horses from database
        double allTotalRussian = calculateAllTotal(allHorses, Leagues.RUSSIA);
        HashMap<String, Double> russia = new HashMap<>();
        allHorses.forEach(horse -> {

            //every horse get his total value
            int total = calculateTotal(horse);

            //4 Pferde pro Liga in Champions League und daraus 2 Rennen mit jeweils 3 Random pferden aus den 8


            double quote;
            if (horse.getLeague() == Leagues.RUSSIA) {
                quote = (allTotalRussian - total) / total;
                quote = Math.round(quote * 100.0) / 100.0;
                russia.put(horse.getName(), quote);
            }

        });
        model.addAttribute("russia", russia);


        return russia;
    }






    public HashMap<String, Double> calculateQuotesChampions(Model model) {

        List<HorseModel> allHorses = horseRepository.findAll(); // Load all horses from database
        int allTotalChampionsLeague = calculateAllTotalChampion(allHorses, Leagues.CHAMPIONS_LEAGUE);
        HashMap<String, Double> champions = new HashMap<>();
        allHorses.forEach(horse -> {

            //every horse get his total value
            int total = calculateTotal(horse);

            //4 Pferde pro Liga in Champions League und daraus 2 Rennen mit jeweils 3 Random pferden aus den 8


            double quote = 0;
            if (horse.getLeague() == Leagues.CHAMPIONS_LEAGUE) {
                quote = (allTotalChampionsLeague - total) / total;
                quote = Math.round(quote * 10 )/ 10;
                champions.put(horse.getName(), quote);
            }

        });
        model.addAttribute("champions", champions);


        return champions;

    }

    private int calculateTotal(HorseModel horseModel) {
        return horseModel.getSpeed() +
                horseModel.getReaction() +
                horseModel.getBalance() +
                horseModel.getEndurance() +
                horseModel.getStarts();
    }

    private double calculateAllTotal(List<HorseModel> allHorses, Leagues league) {
        return allHorses.stream()
                .filter(horse -> horse.getLeague() == league)
                .map(this::calculateTotal)
                .reduce(0, Integer::sum);
    }

    private int calculateAllTotalChampion(List<HorseModel> allHorses, Leagues league) {

        Random rand = new Random();
        int numberOfElements = 3;

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(allHorses.size());
            HorseModel randomElement = allHorses.get(randomIndex);
            allHorses.remove(randomIndex);

            if (i == numberOfElements) {
                return allHorses.stream()
                        .filter(horse -> randomElement.getLeague() == league)
                        .map(this::calculateTotal)
                        .reduce(0, Integer::sum);
            } else {
                //nothing
            }
        }
        return 0;
    }
}
