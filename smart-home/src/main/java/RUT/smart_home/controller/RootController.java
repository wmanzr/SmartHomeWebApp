package RUT.smart_home.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class RootController {

    @GetMapping
    public RepresentationModel<?> getRoot() {
        RepresentationModel<?> rootModel = new RepresentationModel<>();
        rootModel.add(
                linkTo(methodOn(CommandController.class).getAllCommands(null, 0, 10)).withRel("commands"),
                linkTo(methodOn(DeviceController.class).getAllDevices()).withRel("devices"),
                linkTo(methodOn(SensorController.class).getAllSensors()).withRel("sensors"),
                linkTo(methodOn(SensorReadingController.class).getAllReadings(null, 0, 10)).withRel("commands"),
                Link.of("/swagger-ui.html").withRel("documentation")
        );
        return rootModel;
    }
}