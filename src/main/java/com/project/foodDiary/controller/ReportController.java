package com.project.foodDiary.controller;

import com.project.foodDiary.entity.enums.ReportType;
import com.project.foodDiary.entity.enums.ResponseMessage;
import com.project.foodDiary.model.util.ApiResponse;
import com.project.foodDiary.model.jwt.JwtService;
import com.project.foodDiary.model.reportObjects.DailyReportObject;
import com.project.foodDiary.model.reportObjects.MealHistory;
import com.project.foodDiary.repository.UserRepository;
import com.project.foodDiary.service.reportServices.ReportHistoryService;
import com.project.foodDiary.service.reportServices.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

@Controller
public class ReportController {
    private final ReportService reportService;
    private final UserRepository userRepository;
    private final ReportHistoryService reportHistoryService;
    private final JwtService jwtService;


    public ReportController(ReportService reportService, UserRepository userRepository,
                            ReportHistoryService reportHistoryService, JwtService jwtService) {
        this.reportService = reportService;
        this.userRepository = userRepository;
        this.reportHistoryService = reportHistoryService;
        this.jwtService = jwtService;
    }


    @GetMapping("/getDailyReport")
    public ResponseEntity<?> createDailyReport(@RequestParam("date") String dateStr,
                                               @RequestHeader ("Authorization") String authHeader) {
        ApiResponse response = new ApiResponse();
        DailyReportObject dailyReportObject;
        LocalDate date;

        if(!authHeader.startsWith("Bearer ")) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Токен отсутсвует или невалидный!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = authHeader.substring(7);
        String userId;
        try {
            userId = jwtService.extractUserId(token);
        } catch (Exception e) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Недействительный токен");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            date = LocalDate.parse(dateStr);
       }
       catch (DateTimeParseException e) {
            response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
            response.setData(e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
       }
        if (!userRepository.existsById((long) Integer.parseInt(userId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с ID " + userId + " не найден.");
        }
        try {
            dailyReportObject = reportHistoryService.getDailyReport(date, Integer.parseInt(userId));
        }
        catch (Exception e) {
            response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
            response.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
       return ResponseEntity.ok(dailyReportObject);
    }

    @GetMapping("/getMealHistory")
    public ResponseEntity<?> createMealHistory(@RequestHeader ("Authorization") String authHeader, @RequestParam ("startdate") LocalDate startDate,
                                               @RequestParam ("enddate") LocalDate endDate){
        ApiResponse response = new ApiResponse();

        if(!authHeader.startsWith("Bearer ")) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Токен отсутсвует или невалидный!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = authHeader.substring(7);
        String userId;
        try {
            userId = jwtService.extractUserId(token);
        } catch (Exception e) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Недействительный токен");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if(!userRepository.existsById((long) Integer.parseInt(userId))){
            response.setStatus(ResponseMessage.NOT_FOUND.getMessage());
            response.setData("Пользователь с id " + userId + " не найден." );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            MealHistory mealHistory = reportHistoryService.getMealHistoryByStartEndDate(Integer.parseInt(userId),
                    startDate, endDate);
            return ResponseEntity.ok(mealHistory);
        }
        catch (IllegalArgumentException e){
            response.setData(ResponseMessage.BAD_REQUEST.getMessage());
            response.setStatus(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception ex){
            response.setData(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage());
            response.setStatus(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @GetMapping("/downloadReport")
    public ResponseEntity<?> downloadReport(@RequestHeader ("Authorization") String authHeader,
                                             @RequestParam("type") String type) {
        ReportType reportType;
        ApiResponse response = new ApiResponse();

        if(!authHeader.startsWith("Bearer ")) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Токен отсутсвует или невалидный!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = authHeader.substring(7);
        String userId;
        try {
            userId = jwtService.extractUserId(token);
        } catch (Exception e) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Недействительный токен");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        try {
            reportType = ReportType.valueOf(type.toUpperCase());
        }catch (IllegalArgumentException e){
            response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
            response.setData("Неправильный формат файла. Поддерживаются только " + Arrays.stream(ReportType.values()).toList());
            return ResponseEntity.badRequest().body(response);
        }

        if(!userRepository.existsById((long) Integer.parseInt(userId))){
            response.setStatus(ResponseMessage.NOT_FOUND.getMessage());
            response.setData("Пользователь с id " + userId + " не найден." );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            MealHistory mealHistory = reportHistoryService.getTotalMealHistory(Integer.parseInt(userId));
            String s =  reportService.generateReport(mealHistory, reportType);
            response.setStatus(ResponseMessage.SUCCESS.getMessage());
            response.setData(s);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            response.setStatus(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage());
            response.setData(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }catch (IllegalArgumentException ex){
            response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
            response.setData(ex.getMessage());
        }catch (Exception ex){
            response.setStatus(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage());
            response.setData(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
