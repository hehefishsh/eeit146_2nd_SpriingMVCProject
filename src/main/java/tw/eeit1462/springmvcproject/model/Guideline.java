package tw.eeit1462.springmvcproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "guideline")
public class Guideline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "guide_id")
	private int guideId;
	
	@Column(name = "guide_title")
	private String guideTitle; 
	
	@Column(name = "guide_content")
	private String guideContent;
	
	@Column(name = "guide_hyperlink")
	private String guideHyperlink;
	
	@Column(name = "guide_other")
    private byte[] guideOther;
	
	public Guideline() {
	}

	
	public Guideline(String guideTitle, String guideContent) {
		super();
		this.guideTitle = guideTitle;
		this.guideContent = guideContent;
	}


	public Guideline(String guideTitle, String guideContent, String guideHyperlink, byte[] guideOther) {
		super();
		this.guideTitle = guideTitle;
		this.guideContent = guideContent;
		this.guideHyperlink = guideHyperlink;
		this.guideOther = guideOther;
	}



	public Guideline(int guideId, String guideTitle, String guideContent, String guideHyperlink, byte[] guideOther) {
		this.guideId = guideId;
		this.guideTitle = guideTitle;
		this.guideContent = guideContent;
		this.guideHyperlink = guideHyperlink;
		this.guideOther = guideOther;
	}


	public int getGuideId() {
		return guideId;
	}


	public void setGuideId(int guideId) {
		this.guideId = guideId;
	}


	public String getGuideTitle() {
		return guideTitle;
	}


	public void setGuideTitle(String guideTitle) {
		this.guideTitle = guideTitle;
	}


	public String getGuideContent() {
		return guideContent;
	}


	public void setGuideContent(String guideContent) {
		this.guideContent = guideContent;
	}


	public String getGuideHyperlink() {
		return guideHyperlink;
	}


	public void setGuideHyperlink(String guideHyperlink) {
		this.guideHyperlink = guideHyperlink;
	}


	public byte[] getGuideOther() {
		return guideOther;
	}


	public void setGuideOther(byte[] guideOther) {
		this.guideOther = guideOther;
	}

}
