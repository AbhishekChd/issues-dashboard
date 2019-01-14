package com.example.issuesdashboard.events;

import com.example.issuesdashboard.github.GithubClient;
import com.example.issuesdashboard.github.RepositoryEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class EventsController {
    private GithubProjectRepository repository;
    private GithubClient githubClient;

    public EventsController(GithubProjectRepository repository, GithubClient githubClient) {
        this.repository = repository;
        this.githubClient = githubClient;
    }

    @GetMapping("/events/{repoName}")
    @ResponseBody
    public RepositoryEvent[] fetchEvents(@PathVariable String repoName) {
        GithubProject project = this.repository.findByRepoName(repoName);
        return this.githubClient.fetchEvents(project.getOrgName(), project.getRepoName()).getBody();
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        List<DashboardEntry> entries = StreamSupport
                .stream(this.repository.findAll().spliterator(), true)
                .map(githubProject -> new DashboardEntry(
                        githubProject,
                        this.githubClient.fetchEventsList(
                                githubProject.getOrgName(),
                                githubProject.getRepoName())
                ))
                .collect(Collectors.toList());
        model.addAttribute("entries", entries);
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("projects",this.repository.findAll());
        return "admin";
    }
}
