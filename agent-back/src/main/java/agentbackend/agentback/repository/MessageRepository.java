package agentbackend.agentback.repository;

import agentbackend.agentback.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySender(String name);
    List<Message> findAllByReceiver(String name);

    List<Message> findAllBySenderAndReceiverOrderByDateAsc(String name, String receiver);

    List<Message> findAllByReceiverAndSenderOrderByDateAsc(String name, String receiver);

    List<Message> findAllBySenderAndReceiverOrderByDateDesc(String name, String receiver);

    List<Message> findAllByReceiverAndSenderOrderByDateDesc(String name, String receiver);
}
