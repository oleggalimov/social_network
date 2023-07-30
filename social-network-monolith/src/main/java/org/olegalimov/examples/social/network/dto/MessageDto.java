package org.olegalimov.examples.social.network.dto;

import java.time.Instant;

public record MessageDto (String from, String to, String text, Instant timeStamp) {
}