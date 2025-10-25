package RUT.smart_home.repository;

import RUT.smart_home.entity.Command;

import java.util.List;

public interface CommandRepository {
    void create(Command command);
    Command findById(Long id);
    List<Command> findAll();
}