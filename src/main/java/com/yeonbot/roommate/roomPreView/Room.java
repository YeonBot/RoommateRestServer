package com.yeonbot.roommate.roomPreView;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String roomNumber;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String trafic;

    @Column(nullable = false)
    private String montlyRent;

    @Column(nullable = false)
    private String desposit;

    @Column(nullable = false)
    private String residencyCondition;

    @Builder
    public Room(String roomNumber, String location, String trafic, String montlyRent, String desposit, String residencyCondition) {
        this.roomNumber = roomNumber;
        this.location = location;
        this.trafic = trafic;
        this.montlyRent = montlyRent;
        this.desposit = desposit;
        this.residencyCondition = residencyCondition;
    }
}
