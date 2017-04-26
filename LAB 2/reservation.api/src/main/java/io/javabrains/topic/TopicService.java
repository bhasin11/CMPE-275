package io.javabrains.topic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	public List<Topic> getAllTopics(){
		List<Topic> topics = new ArrayList<>();
		topicRepository.findAll()
		.forEach(topics::add);
		
		return topics;
	}
	
	public Topic getTopic(String id){
		
		/*for(Topic topic : topics){
			if(topic.getId().equals(id)) return topic;
		}
		return null;
		*/
		return topicRepository.findOne(id);
	}

	public void addTopic(Topic topic) {
		//topics.add(topic);
		
		topicRepository.save(topic);
	}

	public void updateTopic(Topic topic, String id) {
		
		/*
		 * for(int i=0;i<topics.size();i++){
		 
			if(topics.get(i).getId().equals(id)){
				topics.set(i, topic);
				return;
			}
		}
		*/
		// save inserts and updates too
		topicRepository.save(topic);
	}

	public void deleteTopic(String id) {
		/*
		 * for(int i=0;i<topics.size();i++){
		 
			if(topics.get(i).getId().equals(id)){
				topics.remove(i);
				return;
			}
		}
		*/
		topicRepository.delete(id);
	}
}
