package RUT.smart_home;

import RUT.smart_home.entity.Command;
import RUT.smart_home.entity.Sensor;
import RUT.smart_home.entity.SensorReading;
import RUT.smart_home.entity.Device;
import RUT.smart_home.repository.*;
import RUT.smart_home_contract.api.dto.*;
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

            Device blinds = new Device(
                    "Шторы",
                    DeviceType.BLINDS,
                    "Гостиная",
                    null,
                    DeviceStatus.STANDBY
            );

            Device alarm = new Device(
                    "Сигнализация",
                    DeviceType.ALARM,
                    "Прихожая",
                    null,
                    DeviceStatus.OFF
            );

            Device conditioner = new Device(
                    "Кондиционер",
                    DeviceType.CONDITIONER,
                    "Гостиная",
                    null,
                    DeviceStatus.OFF
            );

            Device humidifier = new Device(
                    "Увлажнитель воздуха",
                    DeviceType.HUMIDIFIER,
                    "Кухня",
                    null,
                    DeviceStatus.OFF
            );

            deviceRepository.create(blinds);
            deviceRepository.create(alarm);
            deviceRepository.create(conditioner);
            deviceRepository.create(humidifier);

            Sensor tempLiving = new Sensor(
                    "Датчик температуры",
                    SensorType.TEMPERATURE,
                    "Гостиная"
            );

            Sensor motionHall = new Sensor(
                    "Датчик движения",
                    SensorType.MOTION,
                    "Прихожая"
            );

            Sensor humidityKitchen = new Sensor(
                    "Датчик влажности",
                    SensorType.HUMIDITY,
                    "Кухня"
            );

            Sensor light_level = new Sensor(
                    "Датчик освещенности",
                    SensorType.LIGHT_LEVEL,
                    "Гостиная"
            );

            sensorRepository.create(tempLiving);
            sensorRepository.create(motionHall);
            sensorRepository.create(humidityKitchen);
            sensorRepository.create(light_level);

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

            Command command1 = new Command(
                    blinds,
                    CommandAction.TURN_ON,
                    null,
                    CommandStatus.SUCCESS,
                    "Шторы закрыты"
            );

            Command command2 = new Command(
                    conditioner,
                    CommandAction.SET_TEMPERATURE,
                    "23",
                    CommandStatus.SUCCESS,
                    "Температура установлена на 23°C"
            );

            commandRepository.create(command1);
            commandRepository.create(command2);

            System.out.println("Тестовые данные успешно созданы!");
        } else {
            System.out.println("В базе уже есть данные, инициализация пропущена.");
        }
    }
}