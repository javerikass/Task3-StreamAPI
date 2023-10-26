package by.clevertec;

import static java.util.stream.Collectors.averagingInt;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.CarUtil;
import by.clevertec.util.Util;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    task1();
    task2();
    task3();
    task4();
    task5();
    task6();
    task7();
    task8();
    task9();
    task10();
    task11();
    task12();
    task13();
    task14();
    task15();
    task16();
    task17();
    task18();
    task19();
    task20();
    task21();
    task22();
  }

  public static void task1() {
    List<Animal> animals = Util.getAnimals();
    List<Animal> list = animals.stream().filter(a -> a.getAge() >= 10 && a.getAge() <= 20)
        .sorted(Comparator.comparingInt(Animal::getAge))
        .skip(14)
        .limit(7).toList();
    System.out.println(list);
  }

  public static void task2() {
    List<Animal> animals = Util.getAnimals();
    List<String> list = animals.stream()
        .filter(o -> o.getOrigin().equals("Japanese"))
        .map(a -> {
          if (a.getGender().equals("Female")) {
            return a.getBread().toUpperCase();
          } else {
            return a.getBread();
          }
        }).toList();
    System.out.println(list);
  }

  public static void task3() {
    List<Animal> animals = Util.getAnimals();
    List<String> list = animals.stream()
        .filter(a -> a.getAge() > 30 && a.getOrigin().startsWith("A"))
        .map(Animal::getOrigin).distinct().toList();
    System.out.println(list);
  }

  public static void task4() {
    List<Animal> animals = Util.getAnimals();
    long female = animals.stream().filter(a -> a.getGender().equals("Female")).count();
    System.out.println(female);
  }

  public static void task5() {
    List<Animal> animals = Util.getAnimals();
    boolean hungarian = animals.stream()
        .anyMatch(a ->
            a.getAge() >= 20
                && a.getAge() <= 30
                && a.getOrigin().equals("Hungarian"));
    System.out.print(
        "Есть ли среди них хоть один из страны Венгрия (Hungarian)? " + "Ответ: " + hungarian);
  }

  public static void task6() {
    List<Animal> animals = Util.getAnimals();
    boolean areAllFemaleAndMale = animals.stream()
        .allMatch(a -> a.getGender().equals("Female") || a.getGender().equals("Male"));
    System.out.print("Все ли животные пола Male и Female ? " + "Ответ: " + areAllFemaleAndMale);
  }

  public static void task7() {
    List<Animal> animals = Util.getAnimals();
    boolean b = animals.stream().noneMatch(a -> a.getOrigin().equals("Oceania"));
    System.out.print("Ни одно из животных не имеет страну происхождения Oceania. " + "Ответ: " + b);
  }

  public static void task8() {
    List<Animal> animals = Util.getAnimals();
    int age = animals.stream().sorted(Comparator.comparing(Animal::getBread))
        .limit(100).max(Comparator.comparingInt(Animal::getAge))
        .orElseThrow().getAge();
    System.out.println(age);
  }

  public static void task9() {
    List<Animal> animals = Util.getAnimals();
    int length = animals.stream().map(Animal::getBread)
        .sorted(Comparator.comparingInt(String::length))
        .map(String::toCharArray)
        .toList().get(0).length;
    System.out.println(length);
  }

  public static void task10() {
    List<Animal> animals = Util.getAnimals();
    int sum = animals.stream().mapToInt(Animal::getAge).sum();
    System.out.println(sum);
  }

  public static void task11() {
    List<Animal> animals = Util.getAnimals();
    double indonesian = animals.stream().filter(a -> a.getOrigin().equals("Indonesian"))
        .mapToInt(Animal::getAge).average().orElseThrow();
    System.out.println(indonesian);
  }

  public static void task12() {
    List<Person> persons = Util.getPersons();
    LocalDate eighteenYears = LocalDate.now().minusYears(18);
    LocalDate twentySevenYears = LocalDate.now().minusYears(27);

    List<Person> list = persons.stream().filter(p -> p.getGender().equals("Male")
            && p.getDateOfBirth().isBefore(eighteenYears)
            && p.getDateOfBirth().isAfter(twentySevenYears))
        .sorted(Comparator.comparing(Person::getRecruitmentGroup))
        .limit(200).toList();

    System.out.println(list);
  }

  public static void task13() {
    List<House> houses = Util.getHouses();
    List<Person> evacuation = new ArrayList<>();

    LocalDate children = LocalDate.now().minusYears(18);
    LocalDate pensioners = LocalDate.now().minusYears(63);

    List<Person> list = houses.stream()
        .peek(p -> {
          if (p.getBuildingType().equals("Hospital")) {
            evacuation.addAll(p.getPersonList());
          }
        })
        .filter(p -> p.getBuildingType().equals("Civil building"))
        .flatMap(p -> p.getPersonList().stream()).peek(p -> {
          if (p.getDateOfBirth().isAfter(children) ||
              p.getDateOfBirth().isBefore(pensioners)) {
            evacuation.add(p);
          }
        }).filter(p -> !p.getDateOfBirth().isAfter(children) ||
            !p.getDateOfBirth().isBefore(pensioners)).toList();

    evacuation.addAll(list.subList(0, 500 - evacuation.size()));
    System.out.println(evacuation);
  }

  public static void task14() {
    List<Car> cars = Util.getCars();
    Map<String, List<Car>> map = CarUtil.getRouteMap();
    Map<String, Predicate<Car>> predicatesMap = CarUtil.getCarPredicatesMap();
    double sum = 0.0;

    Map<String, Double> collect = map.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey, entry -> cars.stream()
                .filter(predicatesMap.get(entry.getKey()))
                .mapToDouble(Car::getMass)
                .sum()
        )).entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getKey, entry -> entry.getValue() * 7.14 / 1000
        ));

    for (Map.Entry<String, Double> entry : collect.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
      sum += entry.getValue();
    }
    System.out.println("Total sum: " + sum);
  }

  public static void task15() {
    List<Flower> flowers = Util.getFlowers();
    double cubicMeterOfWaterPrice = 1.39;
    int fiveYearsInDays = 5 * 365;
    double sum = flowers.stream().sorted(Comparator.comparing(Flower::getOrigin).reversed()
            .thenComparing(Flower::getPrice)
            .thenComparing(Flower::getWaterConsumptionPerDay).reversed())
        .filter(flower ->
            flower.getCommonName().startsWith("S")
                && flower.getCommonName().endsWith("c")
                && flower.isShadePreferred()
                && flower.getFlowerVaseMaterial().contains("Steel")
                && flower.getFlowerVaseMaterial().contains("Glass")
                && flower.getFlowerVaseMaterial().contains("Aluminum"))
        .mapToDouble(
            flower -> flower.getPrice() + (fiveYearsInDays * flower.getWaterConsumptionPerDay()
                * cubicMeterOfWaterPrice)).sum();
    System.out.println(sum);
  }

  public static void task16() {
    List<Student> students = Util.getStudents();
    List<Student> list = students.stream().filter(s -> s.getAge() <= 18)
        .sorted(Comparator.comparing(Student::getSurname)).toList();
    System.out.println(list);
  }

  public static void task17() {
    List<Student> students = Util.getStudents();
    List<String> list = students.stream().map(Student::getGroup).distinct().toList();
    System.out.println(list);
  }

  public static void task18() {
    List<Student> students = Util.getStudents();
    TreeMap<String, Double> collect = students.stream().collect(Collectors.collectingAndThen(
        Collectors.groupingBy(Student::getFaculty, averagingInt(Student::getAge)),
        m -> {
          TreeMap<String, Double> treeMap = new TreeMap<>(Comparator.comparing(m::get));
          treeMap.putAll(m);
          return treeMap;
        }));

    for (Map.Entry<String, Double> entry : collect.entrySet()) {
      System.out.println("Faculty: " + entry.getKey() + ", Average Age: " + entry.getValue());
    }
  }

  public static void task19() {
    List<Student> students = Util.getStudents();
    List<Examination> examinations = Util.getExaminations();
    String groupName = "C-3";

    List<Student> list = students.stream().filter(
            s -> examinations.stream().noneMatch(e -> s.getId() == e.getStudentId()
                && e.getExam3() > 4)
                && s.getGroup().equals(groupName))
        .toList();

    System.out.println(list);
  }

  public static void task20() {
    List<Student> students = Util.getStudents();
    List<Examination> examinations = Util.getExaminations();
    String key = students.stream().collect(Collectors.groupingBy(Student::getFaculty,
            Collectors.averagingDouble(student ->
                examinations.stream()
                    .filter(examination -> examination.getStudentId() == student.getId())
                    .mapToDouble(Examination::getExam1).findFirst().orElse(0.0)
            )
        )).entrySet().stream()
        .max(Map.Entry.comparingByValue()).orElseThrow().getKey();
    System.out.println(key);
  }

  public static void task21() {
    List<Student> students = Util.getStudents();
    Map<String, Long> collect = students.stream()
        .collect(Collectors.groupingBy(Student::getGroup, Collectors.counting()));
    System.out.println(collect);
  }

  public static void task22() {
    List<Student> students = Util.getStudents();
    Map<String, Optional<Integer>> collect = students.stream()
        .collect(Collectors.groupingBy(Student::getFaculty,
            Collectors.mapping(Student::getAge, Collectors.minBy(Comparator.naturalOrder()))));
    System.out.println(collect);
  }
}
