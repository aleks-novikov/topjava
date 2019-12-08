package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
//readOnly = true обеспечивает оптимизацию запросов к БД
//readOnly = true применяется для всех методов класса, кроме тех, у которых @Transactional не прописан явно
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    //аннотация предназаначена для связывания менеджера сущностей с EntityManagerFactory
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User userRef = em.getReference(User.class, userId);

        if (meal.isNew()) {
            meal.setUser(userRef);
            em.persist(meal);
            return meal;
        } else {
            if (get(meal.getId(), userId) == null)
                return null;

            if (meal.getUser() == null)
                meal.setUser(userRef);

            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                 .setParameter("id", id).setParameter("userId", userId)
                 .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meal = em.createNamedQuery(Meal.GET, Meal.class)
                            .setParameter("id", id).setParameter("userId", userId)
                            .getResultList();
        return DataAccessUtils.singleResult(meal);
    }

    @Override
    @Transactional
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL_SORTED, Meal.class)
                 .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                 .setParameter("userId", userId).setParameter("startDate", startDate)
                 .setParameter("endDate", endDate).getResultList();
    }
}