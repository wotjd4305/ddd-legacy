package kitchenpos.infra;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FakeProfanityClient implements ProfanityClient {
    private List<String> profanities;

    public FakeProfanityClient() {
        this(List.of());
    }

    public FakeProfanityClient(List<String> profanities) {
        this.profanities = profanities;
    }

    @Override
    public boolean containsProfanity(final String text) {
        return profanities.stream().anyMatch(profanity -> profanity.contains(text));
    }

    public void setProfanities(final List<String> profanities) {
        this.profanities = profanities;
    }
}
