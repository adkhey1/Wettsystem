package com.carmesin.wettsystem.bet.service;

import com.carmesin.wettsystem.bet.model.HorseModel;
import com.carmesin.wettsystem.bet.model.HorseWithQuote;
import com.carmesin.wettsystem.bet.model.Leagues;
import com.carmesin.wettsystem.bet.repository.HorseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BetService {

    @Autowired
    private HorseRepository horseRepository;

    /**
     * Calculate all quotes from thr horses (round on two digits)
     * Put them in a HashMap
     *
     * @return HashMap with Key = League / Value = HorseModel + Quote of the Horse
     */
    public HashMap<String, List<HorseWithQuote>> calculateQuotes() {

        List<HorseModel> allHorses = horseRepository.findAll(); // Load all horses from database
        //sum of all the total values of the russian horses
        double allTotalRussia = calculateAllTotal(allHorses, Leagues.RUSSIA);
        //sum of all the total values of the german horses
        double allTotalGerman = calculateAllTotal(allHorses, Leagues.GERMAN);

        List<HorseWithQuote> russianLeague = new ArrayList<>();
        List<HorseWithQuote> germanLeague = new ArrayList<>();
        allHorses.stream().forEach(horse -> {

            //every horse get his total value
            int total = calculateTotal(horse);

            double quote;
            if (horse.getLeague() == Leagues.RUSSIA) {
                quote = (allTotalRussia - total) / total;
                quote = Math.round(quote * 100.0) / 100.0;
                russianLeague.add(new HorseWithQuote(horse, quote));
            } else {
                quote = (allTotalGerman - total) / total;
                quote = Math.round(quote * 100.0) / 100.0;
                germanLeague.add(new HorseWithQuote(horse, quote));
            }


        });

        HashMap<String, List<HorseWithQuote>> response = new HashMap<>();
        response.put("russianLeague", russianLeague);
        response.put("germanLeague", germanLeague);

        return response;

    }

    //not in use jet
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
                quote = Math.round(quote * 10) / 10;
                champions.put(horse.getName(), quote);
            }

        });
        model.addAttribute("champions", champions);


        return champions;

    }

    /**
     * adds the integer values together to create a total value
     * Value to calculate the quote
     *
     * @param horseModel to calculate with the values
     * @return TotalValue of the Horse (int)
     */
    private int calculateTotal(HorseModel horseModel) {
        return horseModel.getSpeed() +
                horseModel.getReaction() +
                horseModel.getBalance() +
                horseModel.getEndurance() +
                horseModel.getStarts();
    }

    /**
     * Adds up all total values of the horses (German and Russian leagues separately)
     *
     * @param allHorses to calculate with the values
     * @param league to differentiate the league
     * @return
     */
    private double calculateAllTotal(List<HorseModel> allHorses, Leagues league) {
        return allHorses.stream()
                .filter(horse -> horse.getLeague() == league)
                .map(this::calculateTotal)
                .reduce(0, Integer::sum);
    }

    //not in use jet
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

    /**
     * checks which horse has been bet on
     * result of the bet
     *
     * @param germanBet name of the german horse bet or null
     * @param russianBet name of the russian horse bet or null
     * @return winning = rigth bet / losing = wrong bet
     */
    public String placeBet(String germanBet, String russianBet) {

        HashMap<String, List<HorseWithQuote>> quotes = calculateQuotes();

        String russianWinner = calculateWinner(quotes.get("russianLeague"));
        String germanWinner = calculateWinner(quotes.get("germanLeague"));

        boolean hasWonRussianLeague = russianWinner.equals(russianBet);
        boolean hasWonGermanLeague = germanWinner.equals(germanBet);
        if (germanBet.isEmpty() && hasWonRussianLeague) {
            return "winning";
        } else if (russianBet.isEmpty() && hasWonGermanLeague) {
            return "winning";
        } else if (hasWonRussianLeague && hasWonGermanLeague) {
            return "winning";
        } else {
            return "losing";
        }

    }

    /**
     *Calculate the Winner for the german race and the russain race separate
     *
     * @param league to calculate winner
     * @return the name of the horse that won
     */
    private String calculateWinner(List<HorseWithQuote> league) {

        //reciprocal value of each quote
        league = league.stream().map(horse -> {
            horse.setQuote(1 / horse.getQuote());
            return horse;
        }).collect(Collectors.toList());

        //sum of each reciprocal value
        double sum = league.stream().map(horse -> horse.getQuote()).reduce(0.0, Double::sum);
        //create a random double in the interval of all qoutes
        double result = Math.random() * sum;

        //stops by the winning horse by each league an returns it
        double counter = 0.0;
        for (HorseWithQuote horse : league) {
            counter += horse.getQuote();
            if (counter > result) {
                return horse.getHorse().getName();
            }
        }

        return "";
    }
}
