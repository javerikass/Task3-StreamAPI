package by.clevertec.util;

import by.clevertec.model.Car;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CarUtil {

  private CarUtil() {
  }

  private static final Predicate<Car> PREDICATE_TURKMENISTAN = car -> (
      car.getCarMake().equals("Jaguar")
          || car.getColor().equals("White"));
  private static final Predicate<Car> PREDICATE_UZBEKISTAN = car -> (car.getMass() < 1500
      && car.getCarMake()
      .matches("BMW|Lexus|Chrysler|Toyota"));
  private static final Predicate<Car> PREDICATE_KAZAKHSTAN = car -> (
      car.getMass() > 4000 && car.getColor().equals("Black")
          || car.getCarMake().matches("GMC|Dodge"));
  private static final Predicate<Car> PREDICATE_KYRGYZSTAN = car -> (car.getReleaseYear() < 1982
      || car.getCarModel().matches("Cherokee|Civic"));
  private static final Predicate<Car> PREDICATE_RUSSIA = car -> (car.getPrice() > 40000
      || !car.getColor()
      .matches("Yellow|Red|Green|Blue"));
  private static final Predicate<Car> PREDICATE_MONGOLIA = car -> (car.getVin().contains("59"));

  public static Map<String, List<Car>> getRouteMap() {
    Map<String, List<Car>> map = new HashMap<>();
    map.put("Turkmenistan", new ArrayList<>());
    map.put("Uzbekistan", new ArrayList<>());
    map.put("Kazakhstan", new ArrayList<>());
    map.put("Kyrgyzstan", new ArrayList<>());
    map.put("Russia", new ArrayList<>());
    map.put("Mongolia", new ArrayList<>());
    return map;
  }

  public static Map<String, Predicate<Car>> getCarPredicatesMap() {
    Map<String, Predicate<Car>> mapPredicates = new HashMap<>();
    mapPredicates.put("Turkmenistan", PREDICATE_TURKMENISTAN);
    mapPredicates.put("Uzbekistan", PREDICATE_UZBEKISTAN);
    mapPredicates.put("Kazakhstan", PREDICATE_KAZAKHSTAN);
    mapPredicates.put("Kyrgyzstan", PREDICATE_KYRGYZSTAN);
    mapPredicates.put("Russia", PREDICATE_RUSSIA);
    mapPredicates.put("Mongolia", PREDICATE_MONGOLIA);
    return mapPredicates;
  }

}
