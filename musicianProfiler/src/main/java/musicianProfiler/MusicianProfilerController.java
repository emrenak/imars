package musicianProfiler;

import java.util.List;

import musicianProfiler.service.MusicianProfilerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicianProfilerController {

	@Autowired
	MusicianProfilerService musicianProfilerService;
	
	@RequestMapping("/musicianProfiler/get")
	public List<MusicianProfile> get() {
		return musicianProfilerService.get();
	}
	
}
