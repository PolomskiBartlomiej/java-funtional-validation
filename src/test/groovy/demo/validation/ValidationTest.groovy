package demo.validation

import spock.lang.Specification
import spock.lang.Unroll

class ValidationTest extends Specification {

    def "IfPresent(_object): _object is null not throw NullPointerException"() {
        when:
        Validation.ifPresent(null)
        then:
        noExceptionThrown()
    }

    def "present(_object): _object is null throw IllegalArgumentException"() {
        when:
        Validation.present(null)
        then:
        thrown(IllegalArgumentException)
    }

    def "valid(_predicate) : _predicate is null throw IllegalArgumentException"() {
        when:
        Validation.ifPresent("A")
                .valid(null)
        then:
        thrown(IllegalArgumentException)
    }

    def "valid(_) : toValid is null no throw Exception"() {
        when:
        Validation.ifPresent(null)
                .valid({ true })
        then:
        noExceptionThrown()
    }

    def "and(_predicate) : _predicate is null throw IllegalArgumentException"() {
        when:
        Validation.ifPresent("A")
                .valid({ true })
                .and(null)
        then:
        thrown(IllegalArgumentException)
    }

    def "and(_) : toValid is null no Exception throw"() {
        when:
        Validation.ifPresent(toValid: null)
                .valid({ true })
                .and({ true })
        then:
        noExceptionThrown()
    }

    def "or(_predicate) : _predicate is null throw IllegalArgumentException"() {
        when:
        Validation.ifPresent("A")
                .valid({ true })
                .or(null)
        then:
        thrown(IllegalArgumentException)
    }

    def "or(_) : toValid is null no Exception throw"() {
        when:
        Validation.ifPresent(null)
                .valid({ true })
                .or({ true })
        then:
        noExceptionThrown()
    }

    def "throwIfNot() : toValid is null no Exception throw"() {
        when:
        Validation.ifPresent(null)
                .valid({ false })
                .throwIfNot({ new IllegalAccessException() })
        then:
        noExceptionThrown()
    }

    def "throwIfNot() : throw Exception when valid fails"() {
        when:
        Validation.ifPresent("A")
                .valid({ false })
                .throwIfNot({ new IllegalArgumentException() })
        then:
        thrown(IllegalArgumentException)
    }

    def "isValid() : toValid is null isValid is true and no Exception throw"() {
        when:
        def bool = Validation.ifPresent(null)
                .valid({ false })
                .isValid()
        then:
        bool
        noExceptionThrown()
    }

    def "isValid() : when validate fails isValid is false"() {
        when:
        def bool = Validation.ifPresent("A")
                .valid({ false })
                .isValid()
        then:
        !bool
        noExceptionThrown()
    }

    @Unroll("#A and #B == #C")
    def "and() is logical AND"() {
        when:
        def bool = Validation.ifPresent("toValid")
                .valid({ A })
                .and({ B })
                .isValid()
        then:
        bool == C

        where:
        A     | B     | C
        true  | true  | true
        true  | false | false
        false | true  | false
        false | false | false
    }

    @Unroll("#A and #B == #C")
    def "or() is logical AND"() {
        when:
        def bool = Validation.ifPresent("toValid")
                .valid({ A })
                .or({ B })
                .isValid()
        then:
        bool == C

        where:
        A     | B     | C
        false | false | false
        false | true  | true
        true  | true  | true
        true  | true  | true
    }


    @Unroll("(#A and #B) or #C == #D")
    def "and() with or() : (A and B) or C == D "() {
        when:
        def bool = Validation.ifPresent("toValid")
                .valid({ A })
                .and({ B })
                .or({C})
                .isValid()
        then:
        bool == D

        where:
        A     | B     | C     | D
        true  | true  | true  | true
        true  | true  | false | true
        true  | false | false | false
        false | true  | true  | true
        false | false | true  | true
        false | false | false | false
    }

    @Unroll("(#A or #B) and #C == #D")
    def "and() with or(): (A or B) and C == D "() {
        when:
        def bool = Validation.ifPresent("toValid")
                .valid({ A })
                .or({ B })
                .and({C})
                .isValid()
        then:
        bool == D

        where:
        A     | B     | C     | D
        true  | true  | true  | true
        true  | true  | false | false
        true  | false | false | false
        false | true  | true  | true
        false | false | true  | false
        false | false | false | false
    }

}
