@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent {
}

public class NotificatioSender{
    public static void main(String[] args) {

        Notifier email = message -> System.out.println("Email: " + message);

        class SMSNotifier implements Notifier, Urgent {
            public void send(String message) {
                System.out.println("SMS: " + message);
            }
        }

        Notifier sms = new SMSNotifier();

        Notifier[] senders = {email, sms};

        String message = "Your exam starts tomorrow.";

        for (Notifier sender : senders) {
            sender.send(message);

            if (sender instanceof Urgent) {
                sender.send(message);
            }
        }
    }
}