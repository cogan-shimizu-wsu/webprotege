package edu.stanford.bmir.protege.web.server.match;

import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLLiteral;

import javax.annotation.Nonnull;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 7 Jun 2018
 */
public class LiteralAnnotationValueMatcher implements Matcher<OWLAnnotationValue> {

    @Nonnull
    private final Matcher<OWLLiteral> literalMatcher;

    public LiteralAnnotationValueMatcher(@Nonnull Matcher<OWLLiteral> literalMatcher) {
        this.literalMatcher = checkNotNull(literalMatcher);
    }

    @Override
    public boolean matches(@Nonnull OWLAnnotationValue value) {
        return value instanceof OWLLiteral && literalMatcher.matches((OWLLiteral) value);
    }

    public static Matcher<OWLAnnotationValue> forLexicalPattern(@Nonnull Pattern pattern) {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forLexicalPattern(pattern));
    }

    public static Matcher<OWLAnnotationValue> forLexicalPredicate(Predicate<String> predicate) {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forPredicate(predicate));
    }

    public static Matcher<OWLAnnotationValue> forHasAnyLangTag() {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forHasAnyLangTag());
    }

    public static Matcher<OWLAnnotationValue> forIsXsdBooleanTrue() {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forXsdBooleanTrue());
    }

    /**
     * Returns a matcher for annotation values that matches if the datatype is an OWL 2 datatype
     * and the lexical value is in the lexical space of the datatype.
     */
    public static Matcher<OWLAnnotationValue> forIsNotInLexicalSpace() {
        return new LiteralAnnotationValueMatcher(new NotMatcher<>(new LiteralInLexicalSpaceMatcher()));
    }

    public static Matcher<OWLAnnotationValue> forHasTrailingWhiteSpace() {
        return forLexicalPattern(Pattern.compile("\\s+$"));
    }

    public static Matcher<OWLAnnotationValue> forHasRepeatedWhiteSpace() {
        return forLexicalPattern(Pattern.compile("\\s+"));
    }

    public static Matcher<OWLAnnotationValue> forLessThan(double value) {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forLessThan(value));
    }

    public static Matcher<OWLAnnotationValue> forLessThanOrEqualTo(double value) {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forLessThanOrEqualTo(value));
    }

    public static Matcher<OWLAnnotationValue> forGreaterThan(double value) {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forGreaterThan(value));
    }

    public static Matcher<OWLAnnotationValue> forGreaterThanOrEqualTo(double value) {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forGreaterThanOrEqualTo(value));
    }

    public static Matcher<OWLAnnotationValue> forEqualTo(double value) {
        return new LiteralAnnotationValueMatcher(LiteralMatcher.forEqualTo(value));
    }

    public static Matcher<OWLAnnotationValue> forAnyValue() {
        return OWLAnnotationValue::isLiteral;
    }
}
