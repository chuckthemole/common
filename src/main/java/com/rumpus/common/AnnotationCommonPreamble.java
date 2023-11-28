package com.rumpus.common;

@interface AnnotationCommonPreamble {
    String author();
    String date();
    String lastModified() default "n/a";
    String lastModifiedBy() default "n/a";
    String[] reviewers();
}
