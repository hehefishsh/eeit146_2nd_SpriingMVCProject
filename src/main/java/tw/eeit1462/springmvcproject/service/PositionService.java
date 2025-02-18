package tw.eeit1462.springmvcproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.eeit1462.springmvcproject.model.Position;
import tw.eeit1462.springmvcproject.repository.PositionRepository;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;

	public List<Position> findAllPosition() {
		List<Position> Positions = positionRepository.findAll();
		return Positions;
	}

	public Position findById(Integer id) {
		Optional<Position> Position = positionRepository.findById(id);
		if (Position != null) {
			return Position.get();
		} else {
			return null;
		}
	}

}
