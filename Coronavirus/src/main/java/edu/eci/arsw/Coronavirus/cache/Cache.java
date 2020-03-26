package edu.eci.arsw.Coronavirus.cache;

import edu.eci.arsw.Coronavirus.model.Country;
import org.javatuples.Triplet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class Cache {

    ArrayList<Country> countries = new ArrayList<>();
    private final CopyOnWriteArrayList<Triplet<String, Timer, String>> countriesList = new CopyOnWriteArrayList<>();

    public void countriesConnection(JSONArray paises){
        JSONObject country = null;
        for(int i = 0; i < paises.length(); i++){
            boolean flag = false;
            country = new JSONObject(paises.get(i).toString());
            for(int j = 0; j < countries.size(); j++){
                if(countries.get(j).getName().equals(country.get("country").toString())){
                    countries.get(j).setMuertos(countries.get(j).getMuertos()+ Integer.parseInt(country.get("deaths").toString()));
                    countries.get(j).setInfectados(countries.get(j).getInfectados()+ Integer.parseInt(country.get("confirmed").toString()));
                    countries.get(j).setCurados(countries.get(j).getCurados()+ Integer.parseInt(country.get("recovered").toString()));
                    flag = true;
                }
            }
            if (!flag){
                Country newCountry = new Country(country.get("country").toString(), Integer.parseInt(country.get("deaths").toString()),Integer.parseInt(country.get("confirmed").toString()),Integer.parseInt(country.get("recovered").toString()));
                countries.add(newCountry);
            }
        }
    }

    public boolean countriesEmpty(){
        if(countries.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void tiempo(String name, String data){
        Timer timer = new Timer();
        final Triplet country = new Triplet<>(name,timer,data);
        countriesList.add(country);
        TimerTask timerTask = new TimerTask(){
            public void run(){
                countriesList.remove(country);
            }
        };
        timer.scheduleAtFixedRate(timerTask,300000, 300000);
    }

    public ArrayList<Country> getCountries(){
        return countries;
    }

    public String countryByName(String country) throws CoronavirusException{
        String objeto = null;
        for(int i = 0; i < countriesList.size(); i++){
            if(countriesList.get(i).getValue0().equals(country)){
                objeto = countriesList.get(i).getValue2();
                break;
            }
        }
        return objeto;
    }

    public Country infoCountry(String country) throws  CoronavirusException{
        for(int i = 0; i < countries.size(); i++){
            if(countries.get(i).getName().equals(country)){
                return countries.get(i);
            }
        }
        throw new CoronavirusException("El pais no se encuentra");
    }

    public String countryByName(String country, String data) throws CoronavirusException{
        String objeto = "";
        for(int i = 0; i < countriesList.size(); i++){
            if(countriesList.get(i).getValue0().equals(country)){
                objeto = countriesList.get(i).getValue2();
                break;
            }
        }
        if(objeto.equals("")){
            tiempo(country,data);
        }
        return objeto;
    }

}
