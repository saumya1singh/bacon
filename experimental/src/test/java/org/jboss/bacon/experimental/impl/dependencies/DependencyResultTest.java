package org.jboss.bacon.experimental.impl.dependencies;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class DependencyResultTest {

    private static final Project generateProject(final String groupArtifactId, final String version) {
        Project project = new Project();
        project.setName(String.format("%s:%s", groupArtifactId, version));
        project.setSourceCodeURL(String.format("%s:%s", groupArtifactId, version));
        project.setSourceCodeRevision(version);
        return project;
    }

    @Test
    void addProjects() {
        HashSet<Project> projects = new HashSet();
        projects.add(generateProject("foo:bar", "1.0.0"));
        projects.add(generateProject("foo:bar", "0.0.1"));
        projects.add(generateProject("baz:qux", "1.0.0"));
        assertThat(projects.size()).isEqualTo(3);
        DependencyResult dr = new DependencyResult();
        dr.setTopLevelProjects(projects);
        assertThat(dr.getTopLevelProjects().size()).isEqualTo(3);
    }

    @Test
    void removeOne() {
        HashSet<Project> projects = new HashSet();
        projects.add(generateProject("foo:bar", "1.0.0"));
        projects.add(generateProject("foo:bar", "0.0.1"));
        projects.add(generateProject("baz:qux", "1.0.0"));
        assertThat(projects.size()).isEqualTo(3);
        DependencyResult dr = new DependencyResult();
        dr.setTopLevelProjects(projects);
        assertThat(dr.getTopLevelProjects().size()).isEqualTo(3);
        dr.removeProjects(new String[] { "foo:bar:1.0.0" });
        assertThat(dr.getTopLevelProjects().size()).isEqualTo(2);
    }

    @Test
    void removeSeveral() {
        HashSet<Project> projects = new HashSet();
        projects.add(generateProject("foo:bar", "1.0.0"));
        projects.add(generateProject("foo:bar", "0.0.1"));
        projects.add(generateProject("foo:bar", "1.1.1"));
        projects.add(generateProject("baz:qux", "1.0.0"));
        projects.add(generateProject("baz:qux", "9.1.7"));
        assertThat(projects.size()).isEqualTo(5);
        DependencyResult dr = new DependencyResult();
        dr.setTopLevelProjects(projects);
        assertThat(dr.getTopLevelProjects().size()).isEqualTo(5);
        dr.removeProjects(new String[] { "foo:bar:1.0.0", "baz:qux:1.0.0", "baz:qux:9.1.7" });
        assertThat(dr.getTopLevelProjects().size()).isEqualTo(2);
    }

    @Test
    void removeNonExistent() {
        HashSet<Project> projects = new HashSet();
        projects.add(generateProject("foo:bar", "1.0.0"));
        projects.add(generateProject("foo:bar", "0.0.1"));
        projects.add(generateProject("foo:bar", "1.1.1"));
        projects.add(generateProject("baz:qux", "1.0.0"));
        projects.add(generateProject("baz:qux", "9.1.7"));
        assertThat(projects.size()).isEqualTo(5);
        DependencyResult dr = new DependencyResult();
        dr.setTopLevelProjects(projects);
        assertThat(dr.getTopLevelProjects().size()).isEqualTo(5);
        dr.removeProjects(new String[] { "foo:bar:0.0.0", "baz:qxx:1.0.0" });
        assertThat(dr.getTopLevelProjects().size()).isEqualTo(5);
    }
}