package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.DTO.Point;
import ru.lct.itmoteam.taskservice.DTO.PointStatistic;
import ru.lct.itmoteam.taskservice.entity.PointEntity;
import ru.lct.itmoteam.taskservice.exception.BadInputDataException;
import ru.lct.itmoteam.taskservice.repository.ApplicationRepo;
import ru.lct.itmoteam.taskservice.repository.IssuanceRepo;
import ru.lct.itmoteam.taskservice.repository.PointRepo;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PointService {
    @Autowired
    private PointRepo pointRepo;
    @Autowired
    private ApplicationRepo applicationRepo;
    @Autowired
    private IssuanceRepo issuanceRepo;

    public PointEntity addPoint(Point point) throws BadInputDataException {
        if (pointRepo.existsByAddress(point.getAddress())) {
            throw new BadInputDataException("Точка с таким адресом уже существует.");
        }
        try {
            return pointRepo.save(PointEntity.toEntity(point));
        } catch (Exception e) {
            throw new BadInputDataException("Не удалось добавить точку.");
        }

    }

    public PointEntity getPointEntityById(Long id) {
        return pointRepo.findById(id).get();
    }

    public Point getPointById(Long id) throws BadInputDataException {
        Optional<PointEntity> pointEntityOptional = pointRepo.findById(id);
        if (pointEntityOptional.isEmpty())
            throw new BadInputDataException("Не существует точки с таким id.");
        return Point.toModel(pointEntityOptional.get());
    }

    public PointEntity getPointByAddress(String address) {
        return pointRepo.findByAddress(address).orElse(null);
    }

    public boolean existsById(Long id) {
        return pointRepo.existsById(id);
    }

    public boolean existsByAddress(String address) {
        return pointRepo.existsByAddress(address);
    }

    public void deletePointById(Long id) {
        pointRepo.deleteById(id);
    }

    public PointStatistic getStatistic(PointEntity point) {
        PointStatistic pointStatistic = new PointStatistic();
        pointStatistic.setId(point.getId());
        pointStatistic.setAddress(point.getAddress());
        pointStatistic.setJoinDate(point.getJoinDate());
        pointStatistic.setMaterialsDelivered(point.isMaterialsDelivered());
        Optional<Date> maxDate = issuanceRepo.findMaxDateByPointId(point.getId());
        if (maxDate.isPresent()) {
            // TODO: normal calculating days
            pointStatistic.setDaysFromLastCardIssuanse((int) Duration.between((Temporal) maxDate.get(), (Temporal) new Date()).toDays());
        } else
            pointStatistic.setDaysFromLastCardIssuanse(0);

        pointStatistic.setApplicationsCount(applicationRepo.getApplicationCountByPointId(point.getId()));
        pointStatistic.setIssuanceCount(issuanceRepo.countByPointId(point.getId()));
        return pointStatistic;
    }

    public List<PointStatistic> getAllPointsStatistic() {
        Iterable<PointEntity> source = pointRepo.findAll();
        List<PointStatistic> pointStatistics = new ArrayList<>();
        for (PointEntity i : source) {
            pointStatistics.add(getStatistic(i));
        }
        return pointStatistics;
    }
}
