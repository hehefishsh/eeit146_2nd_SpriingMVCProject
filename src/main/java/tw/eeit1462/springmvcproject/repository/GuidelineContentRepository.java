package tw.eeit1462.springmvcproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.eeit1462.springmvcproject.model.GuidelineContent;

public interface GuidelineContentRepository extends JpaRepository<GuidelineContent, Integer> {

	public List<GuidelineContent> findByGuidelineGuideId(Integer guideId);
}
