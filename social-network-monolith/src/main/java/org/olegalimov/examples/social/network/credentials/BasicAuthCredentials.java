package org.olegalimov.examples.social.network.credentials;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.concurrent.Executor;

import static org.olegalimov.examples.social.network.constant.CommonConstant.AUTHORIZATION_METADATA_KEY;

@Slf4j
public class BasicAuthCredentials extends CallCredentials {

    private final String basicToken;

    public BasicAuthCredentials(String userName, String password) {
        if(!StringUtils.hasLength(userName) || !StringUtils.hasLength(password)) {
            throw new IllegalStateException("Не заданы логин или пароль для обращения к сервису диалогов!");
        }
        basicToken = "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(
                () -> {
                    try {
                        Metadata headers = new Metadata();
                        headers.put(AUTHORIZATION_METADATA_KEY, basicToken);
                        applier.apply(headers);
                    } catch (Throwable e) {
                        applier.fail(Status.UNAUTHENTICATED.withCause(e));
                    }
                }
        );
    }

    @Override
    public void thisUsesUnstableApi() {
        log.warn("Этот метод не реализован!");
    }
}
