package musicianwealth;

import java.util.List;

import musicianwealth.exception.MusicianWealthNotFoundException;
import musicianwealth.service.MusicianWealthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicianWealthController {

	@Autowired
	MusicianWealthService musicianWealthService;
	
	@RequestMapping("/musicianwealth/get")
	public MusicianWealth get(@RequestParam(value="email", defaultValue="") String email) throws MusicianWealthNotFoundException{
		return musicianWealthService.get(email);
	}
	
	
	@RequestMapping("/musicianwealth/adjustLevel")
	public void adjustMusicianWealth(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="adjustment") int adjustment){
		musicianWealthService.adjustMusicianWealth(email, adjustment);
	}
	
	@RequestMapping("/musicianwealth/addAsset")
	public void addAssets(@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="asset", defaultValue="") String asset,
			@RequestParam(value="numOfAsset") int numOfAsset){
		musicianWealthService.addAssets(email, asset, numOfAsset);
	}
	
	@RequestMapping("/musicianwealth/rankByWealthLevel")
	public List<MusicianWealth> rankByWealthLevel(){
		return musicianWealthService.rankByWealthLevel();
	}
}
