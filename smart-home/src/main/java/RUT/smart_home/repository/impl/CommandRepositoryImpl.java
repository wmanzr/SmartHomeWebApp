package RUT.smart_home.repository.impl;

import RUT.smart_home.entity.Command;
import RUT.smart_home.repository.BaseRepository;
import RUT.smart_home.repository.CommandRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommandRepositoryImpl extends BaseRepository<Command, Long> implements CommandRepository {
    public CommandRepositoryImpl() {
        super(Command.class);
    }
    @Override
    public void create(Command command) {
        super.create(command);
    }
    @Override
    public Command findById(Long id) {
        return super.findById(id);
    }
    @Override
    public List<Command> findAll() {
        return super.findAll();
    }
}