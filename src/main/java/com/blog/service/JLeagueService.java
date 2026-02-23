package com.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JLeagueService
 * - J1 League (league=98)
 */
@Service
@RequiredArgsConstructor
public class JLeagueService {

    private final RestTemplate restTemplate;

    @Value("${football.api.key}")
    private String apiKey;

    @Value("${football.api.url}")
    private String apiUrl;

    /**
     * 2024 시즌 최근 20경기
     */
    public List<Map<String, Object>> getMatch2024() {

        try {
            String url = apiUrl + "/fixtures?league=98&season=2024";

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-apisports-key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getBody() == null ||
                response.getBody().get("response") == null) {
                return new ArrayList<>();
            }

            List<Map<String, Object>> responseList =
                    (List<Map<String, Object>>) response.getBody().get("response");

            List<Map<String, Object>> result = new ArrayList<>();

            if (!responseList.isEmpty()) {

                int startIndex = Math.max(responseList.size() - 20, 0);
                List<Map<String, Object>> recent20 =
                        responseList.subList(startIndex, responseList.size());

                for (Map<String, Object> item : recent20) {

                    Map<String, Object> fixture =
                            (Map<String, Object>) item.get("fixture");

                    Map<String, Object> teams =
                            (Map<String, Object>) item.get("teams");

                    Map<String, Object> goals =
                            (Map<String, Object>) item.get("goals");

                    Map<String, Object> status =
                            (Map<String, Object>) fixture.get("status");

                    Map<String, Object> home =
                            (Map<String, Object>) teams.get("home");

                    Map<String, Object> away =
                            (Map<String, Object>) teams.get("away");

                    Map<String, Object> match = new HashMap<>();

                    match.put("date", fixture.get("date"));
                    match.put("elapsed", status != null ? status.get("elapsed") : null);

                    match.put("homeName", home.get("name"));
                    match.put("homeLogo", home.get("logo"));
                    match.put("awayName", away.get("name"));
                    match.put("awayLogo", away.get("logo"));

                    match.put("homeGoal", goals.get("home"));
                    match.put("awayGoal", goals.get("away"));

                    result.add(match);
                }
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 실시간 경기
     */
    public List<Map<String, Object>> getLiveMatches() {

        try {
            String url = apiUrl + "/fixtures?live=98";

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-apisports-key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getBody() == null ||
                response.getBody().get("response") == null) {
                return new ArrayList<>();
            }

            List<Map<String, Object>> responseList =
                    (List<Map<String, Object>>) response.getBody().get("response");

            List<Map<String, Object>> result = new ArrayList<>();

            for (Map<String, Object> item : responseList) {

                Map<String, Object> fixture =
                        (Map<String, Object>) item.get("fixture");

                Map<String, Object> teams =
                        (Map<String, Object>) item.get("teams");

                Map<String, Object> goals =
                        (Map<String, Object>) item.get("goals");

                Map<String, Object> status =
                        (Map<String, Object>) fixture.get("status");

                Map<String, Object> home =
                        (Map<String, Object>) teams.get("home");

                Map<String, Object> away =
                        (Map<String, Object>) teams.get("away");

                Map<String, Object> match = new HashMap<>();

                match.put("elapsed", status != null ? status.get("elapsed") : null);
                match.put("homeName", home.get("name"));
                match.put("homeLogo", home.get("logo"));
                match.put("awayName", away.get("name"));
                match.put("awayLogo", away.get("logo"));
                match.put("homeGoal", goals.get("home"));
                match.put("awayGoal", goals.get("away"));

                result.add(match);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    /**
     * J1 리그 순위
     */
    public List<Map<String, Object>> getStandings() {

        try {
            String url = apiUrl + "/standings?league=98&season=2024";

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-apisports-key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getBody() == null ||
                response.getBody().get("response") == null) {
                return new ArrayList<>();
            }

            List<Map<String, Object>> responseList =
                    (List<Map<String, Object>>) response.getBody().get("response");

            List<Map<String, Object>> result = new ArrayList<>();

            if (!responseList.isEmpty()) {

                Map<String, Object> league =
                        (Map<String, Object>) responseList.get(0).get("league");

                List<Map<String, Object>> standings =
                        (List<Map<String, Object>>) league.get("standings");

                List<Map<String, Object>> table =
                        (List<Map<String, Object>>) standings.get(0);

                for (Map<String, Object> teamData : table) {

                    Map<String, Object> team =
                            (Map<String, Object>) teamData.get("team");

                    Map<String, Object> all =
                            (Map<String, Object>) teamData.get("all");

                    Map<String, Object> row = new HashMap<>();

                    row.put("rank", teamData.get("rank"));
                    row.put("name", team.get("name"));
                    row.put("logo", team.get("logo"));
                    row.put("points", teamData.get("points"));
                    row.put("played", all.get("played"));
                    row.put("win", all.get("win"));
                    row.put("draw", all.get("draw"));
                    row.put("lose", all.get("lose"));
                    row.put("goalsDiff", teamData.get("goalsDiff"));

                    result.add(row);
                }
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * J1 리그 팀 기본 정보
     */
    public List<Map<String, Object>> getTeams() {

        try {
            String url = apiUrl + "/teams?league=98&season=2024";

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-apisports-key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getBody() == null ||
                response.getBody().get("response") == null) {
                return new ArrayList<>();
            }

            List<Map<String, Object>> responseList =
                    (List<Map<String, Object>>) response.getBody().get("response");

            List<Map<String, Object>> result = new ArrayList<>();

            for (Map<String, Object> item : responseList) {

                Map<String, Object> team =
                        (Map<String, Object>) item.get("team");

                Map<String, Object> venue =
                        (Map<String, Object>) item.get("venue");

                Map<String, Object> map = new HashMap<>();

                // 🔥 이 줄 반드시 필요
                map.put("id", team.get("id"));

                map.put("name", team.get("name"));
                map.put("logo", team.get("logo"));
                map.put("founded", team.get("founded"));
                map.put("country", team.get("country"));

                map.put("stadium", venue.get("name"));
                map.put("city", venue.get("city"));
                map.put("capacity", venue.get("capacity"));

                result.add(map);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public Map<String, Object> getTeamById(Long teamId) {

        try {
            String url = apiUrl + "/teams?id=" + teamId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-apisports-key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getBody() == null ||
                response.getBody().get("response") == null) {
                return null;
            }

            List<Map<String, Object>> responseList =
                    (List<Map<String, Object>>) response.getBody().get("response");

            if (responseList.isEmpty()) return null;

            Map<String, Object> item = responseList.get(0);

            Map<String, Object> team =
                    (Map<String, Object>) item.get("team");

            Map<String, Object> venue =
                    (Map<String, Object>) item.get("venue");

            Map<String, Object> result = new HashMap<>();

            result.put("name", team.get("name"));
            result.put("logo", team.get("logo"));
            result.put("founded", team.get("founded"));
            result.put("country", team.get("country"));
            result.put("stadium", venue.get("name"));
            result.put("city", venue.get("city"));
            result.put("capacity", venue.get("capacity"));

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}