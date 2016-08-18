package hudson.plugins.scm_sync_configuration.scms.customproviders.git.gitexe;

import org.apache.maven.scm.*;
import org.apache.maven.scm.provider.ScmProviderRepository;
import org.apache.maven.scm.provider.git.gitexe.command.checkout.GitCheckOutCommand;
import org.dom4j.Branch;

import java.util.Map;

/**
 * Created by marcelorosa on 18/08/16.
 */
public class ScmSyncGitCheckoutCommand extends GitCheckOutCommand {

    private final String SCM_SYNC_BRANCH = "SCM_SYNC_BRANCH";

    @Override
    public ScmResult executeCommand(ScmProviderRepository repository, ScmFileSet fileSet,
                                    CommandParameters parameters )
            throws ScmException
    {
        Map<String, String> env = System.getenv();
        String branch = "master";
        if (env.containsKey(SCM_SYNC_BRANCH)) {
            branch = env.get(SCM_SYNC_BRANCH);
        }
        ScmVersion scmVersion = new ScmBranch(branch);
        String recursiveParam = parameters.getString( CommandParameter.RECURSIVE, null );
        if ( recursiveParam != null )
        {
            boolean recursive = parameters.getBoolean( CommandParameter.RECURSIVE );
            return executeCheckOutCommand( repository, fileSet, scmVersion, recursive );
        }

        return executeCheckOutCommand( repository, fileSet, scmVersion );
    }
}
