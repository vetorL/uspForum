package com.example.uspForum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SubjectReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private final CustomUser author;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private final Subject subject;

    @OneToMany(mappedBy = "subjectReview")
    private List<Vote> votes = new ArrayList<>();

    @NotBlank(message = "A review deve conter um título!")
    @Size(min = 1, max = 50, message = "Título deve ter entre 1 e 50 caracteres.")
    private final String title;

    @NotBlank
    @Size(min = 1, max = 1000, message = "Conteúdo deve ter entre 1 e 1000 caracteres.")
    private final String content;

    @NotBlank
    @Pattern(regexp="^(Neutro|Recomendo|Não recomendo)$", message="Recomendação inválida")
    private final String recommendation;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    public int getTotalVotes() {
        return votes.stream()
                .mapToInt(Vote::getVote)
                .sum();
    }

    public boolean alreadyVoted(CustomUser principal, String type) {

        if(type.equals("up")) {
            for(Vote vote : votes) {
                if(vote.getVoter().getId() == principal.getId() && vote.getVote() == 1) {
                    return true;
                }
            }
        } else if(type.equals("down")) {
            for(Vote vote : votes) {
                if(vote.getVoter().getId() == principal.getId() && vote.getVote() == -1) {
                    return true;
                }
            }
        }

        return false;
    }

    public String getTimeElapsed() {
        Date now = new Date();

        long diff = TimeUnit.MILLISECONDS.toMillis(now.getTime() - createdAt.getTime());

        int seconds = (int) TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS);
        int minutes = (int) TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
        int hours = (int) TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        int years = days / 365;
        int months = days / 30;

        if (years >= 1) {

            // Case where a year or more has passed
            if (years >= 2) {
                return years + " anos";
            }
            return "1 ano";

        } else if (months >= 1) {

            // Case where a month or more has passed
            if (months >= 2) {
                return months + " meses";
            }
            return "1 mês";

        } else if (days >= 1) {

            // Case where a day or more has passed
            if (days >= 2) {
                return days + " dias";
            }
            return "1 dia";

        } else if (hours >= 1) {

            // Case where an hour or more has passed
            if (hours >= 2) {
                return hours + " horas";
            }
            return "1 hora";

        } else if (minutes >= 1) {

            // Case where a minute or more has passed
            if (minutes >= 2) {
                return minutes + " minutos";
            }
            return "1 minuto";

        } else if (seconds >= 1) {

            // Case where a second or more has passed
            if (seconds >= 2) {
                return seconds + " segundos";
            }
            return "1 segundo";

        } else {

            return "0 segundos";

        }

    }

}
