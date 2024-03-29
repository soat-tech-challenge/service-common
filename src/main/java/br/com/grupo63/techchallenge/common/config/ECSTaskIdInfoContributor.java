package br.com.grupo63.techchallenge.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ECSTaskIdInfoContributor implements InfoContributor {

    @Value("${info.ecs_metadata_url_v4:unknown}")
    String ecsMetadataUrlV4;

    @Override
    public void contribute(Info.Builder builder) {
        try {
            List<String> parts = Arrays.asList(ecsMetadataUrlV4.split("/v4/"));
            String taskId = parts.get(1).split("-")[0];
            builder.withDetail("ecs_task_id", taskId);
        } catch (RuntimeException ignored) {
            builder.withDetail("ecs_task_id", "unknown");
        }
    }
}