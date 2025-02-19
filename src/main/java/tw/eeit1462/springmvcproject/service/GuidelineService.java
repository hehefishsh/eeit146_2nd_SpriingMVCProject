package tw.eeit1462.springmvcproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.eeit1462.springmvcproject.model.Guideline;
import tw.eeit1462.springmvcproject.repository.GuidelineRepository;

@Service
@Transactional
public class GuidelineService {

	@Autowired
	private GuidelineRepository guidelineRepository;

	public List<Guideline> findAllGuideline(){
		List<Guideline> guidelines = guidelineRepository.findAll();
		
		return guidelines;
	}
	
	public Guideline findGuidelineById(Integer id) {
		Optional<Guideline> guideline = guidelineRepository.findById(id);
		
		if(guideline!=null) {
			return guideline.get();
		}else {
			return null;
		}
	}
}
