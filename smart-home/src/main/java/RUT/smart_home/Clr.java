package RUT.smart_home;

import RUT.smart_home.entity.*;
import RUT.smart_home.repository.*;
import RUT.smart_home_contract.api.dto.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class Clr implements CommandLineRunner {

    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;
    private final SensorReadingRepository sensorReadingRepository;
    private final CommandRepository commandRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Clr(
            DeviceRepository deviceRepository,
            SensorRepository sensorRepository,
            SensorReadingRepository sensorReadingRepository,
            CommandRepository commandRepository
    ) {
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.sensorReadingRepository = sensorReadingRepository;
        this.commandRepository = commandRepository;
    }

    private void clearDatabase() {
        entityManager.createNativeQuery("TRUNCATE TABLE command RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE sensor_reading RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE sensor RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE device RESTART IDENTITY CASCADE").executeUpdate();
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        clearDatabase();

        if (deviceRepository.findAll().isEmpty() &&
                sensorRepository.findAll().isEmpty() &&
                commandRepository.findAll().isEmpty()) {

        // ====== Создаем устройства ======
        Device livingLamp = new Device(
                "Лампа в гостиной",
                DeviceType.LIGHT,
                "Гостиная",
                null,
                DeviceStatus.OFF
        );

        Device heater = new Device(
                "Обогреватель",
                DeviceType.HEATER,
                "Гостиная",
                null,
                DeviceStatus.ON
        );

        Device doorLock = new Device(
                "Замок входной двери",
                DeviceType.DOOR_LOCK,
                "Прихожая",
                null,
                DeviceStatus.ON
        );

        Device coffeeMachine = new Device(
                "Кофемашина на кухне",
                DeviceType.CUSTOM,
                "Кухня",
                null,
                DeviceStatus.OFF
        );

        deviceRepository.create(livingLamp);
        deviceRepository.create(heater);
        deviceRepository.create(doorLock);
        deviceRepository.create(coffeeMachine);

        // ====== Создаем сенсоры ======
        Sensor tempLiving = new Sensor(
                "Датчик температуры гостиной",
                SensorType.TEMPERATURE,
                "Гостиная"
        );

        Sensor motionHall = new Sensor(
                "Датчик движения прихожей",
                SensorType.MOTION,
                "Прихожая"
        );

        Sensor humidityKitchen = new Sensor(
                "Датчик влажности кухни",
                SensorType.HUMIDITY,
                "Кухня"
        );

        sensorRepository.create(tempLiving);
        sensorRepository.create(motionHall);
        sensorRepository.create(humidityKitchen);

        // ====== Создаем показания ======
        SensorReading reading1 = new SensorReading(
                tempLiving,
                22.3,
                "°C",
                LocalDateTime.now().minusMinutes(10)
        );

        SensorReading reading2 = new SensorReading(
                motionHall,
                0.0,
                "boolean",
                LocalDateTime.now().minusMinutes(3)
        );

        SensorReading reading3 = new SensorReading(
                humidityKitchen,
                45.0,
                "%",
                LocalDateTime.now()
        );

        sensorReadingRepository.create(reading1);
        sensorReadingRepository.create(reading2);
        sensorReadingRepository.create(reading3);

        // ====== Создаем команды ======
        Command command1 = new Command(
                livingLamp,
                CommandAction.TURN_ON,
                null,
                CommandStatus.SUCCESS,
                "Лампа включена"
        );

        Command command2 = new Command(
                heater,
                CommandAction.SET_TEMPERATURE,
                "23",
                CommandStatus.SUCCESS,
                "Температура установлена на 23°C"
        );

        Command command3 = new Command(
                doorLock,
                CommandAction.LOCK,
                null,
                CommandStatus.SUCCESS,
                "Замок активирован"
        );

        Command command4 = new Command(
                coffeeMachine,
                CommandAction.TURN_ON,
                null,
                CommandStatus.PENDING,
                "Запуск кофемашины"
        );

        commandRepository.create(command1);
        commandRepository.create(command2);
        commandRepository.create(command3);
        commandRepository.create(command4);

            System.out.println("Тестовые данные успешно созданы!");
        } else {
            System.out.println("В базе уже есть данные, инициализация пропущена.");
        }
    }
}