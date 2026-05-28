import type { CourseListItem } from '@/types';

function isCourseListItem(value: unknown): value is CourseListItem {
  if (typeof value !== 'object' || value === null) {
    return false;
  }

  const course = value as Record<string, unknown>;

  return (
    typeof course.id === 'number' &&
    typeof course.name === 'string' &&
    typeof course.slug === 'string' &&
    (typeof course.description === 'string' || course.description === null) &&
    (typeof course.thumbnail === 'string' || course.thumbnail === null)
  );
}

export function parseCoursesList(data: unknown): CourseListItem[] {
  if (!Array.isArray(data)) {
    throw new Error('Courses response must be an array');
  }

  if (!data.every(isCourseListItem)) {
    throw new Error('Invalid course item in API response');
  }

  return data;
}
