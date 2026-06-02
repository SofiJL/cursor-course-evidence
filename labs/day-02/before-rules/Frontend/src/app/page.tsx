import Link from 'next/link';
import styles from './page.module.scss';
import { Course } from '@/components/Course/Course';
import { parseCoursesList } from '@/lib/validateCourses';

export const dynamic = 'force-dynamic';

async function getCourses() {
  let res: Response;

  try {
    res = await fetch('http://localhost:8000/courses', { cache: 'no-store' });
  } catch {
    throw new Error(
      'Failed to fetch courses: no se pudo conectar con http://localhost:8000'
    );
  }

  if (!res.ok) {
    throw new Error(`Failed to fetch courses: ${res.status} ${res.statusText}`);
  }

  const data: unknown = await res.json();
  return parseCoursesList(data);
}

export default async function Home() {
  const courses = await getCourses();

  return (
    <div className={styles.page}>
      <header className={styles.header}>
        <h1 className={styles.title}>Platzi Flix — Ejemplo pruebas</h1>
      </header>

      <main className={styles.main}>
        {courses.length === 0 ? (
          <p className={styles.emptyState}>
            No hay cursos disponibles por el momento.
          </p>
        ) : (
          <div className={styles.coursesGrid}>
            {courses.map((course) => (
              <Link
                key={course.id}
                href={`/course/${course.slug}`}
                className={styles.courseLink}
              >
                <Course
                  id={course.id}
                  name={course.name}
                  description={course.description}
                  thumbnail={course.thumbnail}
                />
              </Link>
            ))}
          </div>
        )}
      </main>

      <div className={styles.gridBg} aria-hidden="true" />
    </div>
  );
}
