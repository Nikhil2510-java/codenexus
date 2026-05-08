package com.bhasaka.codenexus.core.services;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = WorkflowProcess.class,
        property = {
                "process.label=Update Asset Metadata"
        }
)
public class UpdateMetadataProcess implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateMetadataProcess.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession,
                        MetaDataMap metaDataMap) throws WorkflowException {

        try {
            String payloadPath = workItem.getWorkflowData().getPayload().toString();
            ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);

            if (resolver == null) {
                LOG.error("ResourceResolver is null");
                return;
            }

            Resource metadataRes = resolver.getResource(payloadPath + "/jcr:content/metadata");

            if (metadataRes == null) {
                LOG.error("Metadata resource not found at path: {}", payloadPath);
                return;
            }

            // Safe property access
            Object isAdmin = metadataRes.getValueMap().get("isAdmin");
            Object isAuthor = metadataRes.getValueMap().get("isAuthor");

            if (isAdmin != null) {
                LOG.info("Value selected is Admin");
            }

            if (isAuthor != null) {
                LOG.info("Value selected is Author");

                // Workflow model path
                String workflowModelPath = "/var/workflow/models/finalReviewerWorkFlow";

                com.adobe.granite.workflow.model.WorkflowModel model =
                        workflowSession.getModel(workflowModelPath);

                if (model == null) {
                    LOG.error("Workflow model not found: {}", workflowModelPath);
                    return;
                }

                // Prepare payload
                com.adobe.granite.workflow.exec.WorkflowData wfData =
                        workflowSession.newWorkflowData("JCR_PATH", payloadPath);

                // Start workflow
                workflowSession.startWorkflow(model, wfData);

                LOG.info("Triggered second workflow for Author case");
            }

        } catch (Exception e) {
            LOG.error("Error in UpdateMetadataProcess", e);
            throw new WorkflowException(e);
        }
    }
}
