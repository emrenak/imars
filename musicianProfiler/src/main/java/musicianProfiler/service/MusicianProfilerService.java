package musicianProfiler.service;

import java.util.List;

import musicianProfiler.MusicianProfile;

import org.bson.Document;

public interface MusicianProfilerService {

	public void profile(int id, List<Document> musicianList);
	public List<MusicianProfile> get();
}
