package musicianProfiler.service;

import java.util.List;

import org.bson.Document;

public interface MusicianProfilerService {

	public void profile(int id, List<Document> musicianList);
}
