//package club.gach_dong.domain;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Getter;
//
//@Entity
//@Getter
//@Table(name = "notification_templates")
//public class NotificationTemplate {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String type;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String content;
//}