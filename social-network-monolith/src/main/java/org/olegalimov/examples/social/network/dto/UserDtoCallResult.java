package org.olegalimov.examples.social.network.dto;

import io.tarantool.driver.api.SingleValueCallResult;

import java.util.List;

public interface UserDtoCallResult extends SingleValueCallResult<List<UserDto>> {
}